package com.company.view.container;

import com.company.Main;
import com.company.shapeMaker.ShapeMaker;
import com.company.shapeMaker.ShapeModes;
import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Toolbar extends JPanel implements ChangeListener {

    ShapeMaker currentShape;

    public Toolbar(ShapeMaker currentShape){
        this.currentShape = currentShape;
        init();
    }

    private JLabel strokeSetterText;
    private JSpinner strokeSetter;

    private ArrayList<JButton> buttons;

    @Getter private JButton pencil;
    @Getter private JButton bucket;
    @Getter private JButton eraser;
    @Getter private JButton[] shapeButtons;
    @Getter private final Color clickedColor = Color.decode("#c0cce4");

    private void init() {
        buttons = new ArrayList<>();

        this.setBackground(Color.lightGray);

        JPanel shapes = new JPanel();
        shapes.setBorder(BorderFactory.createLineBorder(Color.black));

        SpinnerModel strokeValues = new SpinnerNumberModel(5, 1, 99, 1);
        strokeSetter = new JSpinner(strokeValues);
        strokeSetter.addChangeListener(this);

        strokeSetterText = new JLabel("Stroke:");

        shapeButtons = new JButton[3];
        JButton lineDraw = buttonMaker(action(e -> {
            currentShape.setMode(ShapeModes.LINE);
            currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth()));
        }));
        setIcon(lineDraw, "line.png", "Line");
        shapes.add(lineDraw);
        lineDraw.setBackground(clickedColor);
        lineDraw.doClick();
        shapeButtons[0] = lineDraw;

        JButton rectangleDraw = buttonMaker(action(e -> {
            currentShape.setMode(ShapeModes.RECTANGLE);
            currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth()));
        }));
        setIcon(rectangleDraw, "rectangle.png", "Rectangle");
        shapes.add(rectangleDraw);
        shapeButtons[1] = rectangleDraw;

        JButton circleDraw = buttonMaker(action(e -> {
            currentShape.setMode(ShapeModes.CIRCLE);
            currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth()));
        }));
        setIcon(circleDraw, "circle.png", "Circle");
        shapes.add(circleDraw);
        shapeButtons[2] = circleDraw;

        this.add(shapes);

        pencil = buttonMaker(action(e-> {
            currentShape.setMode(ShapeModes.BRUSH);
            currentShape.setStrokeWidth((Integer) strokeSetter.getValue());

            currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth(),
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        }));
        setIcon(pencil, "pencil.png", "Pencil");

        this.add(pencil);
        this.add(strokeSetterText);
        this.add(strokeSetter);

        bucket = buttonMaker(action(e->currentShape.setMode(ShapeModes.BUCKET)));
        setIcon(bucket, "bucket.png", "Bucket");


        this.add(bucket);

         eraser = buttonMaker(action(e->{
            currentShape.setMode(ShapeModes.ERASER);
            currentShape.setStrokeWidth((Integer) strokeSetter.getValue());

            currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        }));

        setIcon(eraser, "eraser.png", "Eraser");
        this.add(eraser);

        colorChooserMaker(e->{
            Color newColor = JColorChooser.showDialog(
                    Main.getFrame(),
                    "Choose Color",
                    currentShape.getColor());
            if(newColor != null) {
                currentShape.setColor(newColor);
                JButton source = (JButton) e.getSource();
                source.setBackground(currentShape.getColor());
            }
        }, "FG", currentShape.getColor());

        colorChooserMaker(e->{
            Color newColor = JColorChooser.showDialog(
                    Main.getFrame(),
                    "Choose Color",
                    currentShape.getColor());
            if(newColor != null) {
                currentShape.setBgColor(newColor);
                JButton source = (JButton) e.getSource();
                source.setBackground(currentShape.getBgColor());
            }
        }, "BG", currentShape.getBgColor());

    }

    public void setIcon(JButton button, String imgName, String backupText){
        try {
            Image icon = ImageIO.read(new File("./res/img/" + imgName));
            icon = icon.getScaledInstance(15, 16,
                    Image.SCALE_SMOOTH); // scale the image so it fits nicely into the button
            button.setIcon(new ImageIcon(icon));
        } catch (IOException e) {
            button.setPreferredSize(null);
            button.setText(backupText);
        }
    }

    public void colorChooserMaker(ActionListener action, String text, Color bgColor){
        JButton colorChooser = buttonMaker(action);
        colorChooser.setBackground(bgColor);
        colorChooser.setFocusPainted(false);
        colorChooser.setAlignmentX(JButton.CENTER_ALIGNMENT);
        colorChooser.setPreferredSize(new Dimension(30,30));

        buttons.remove(colorChooser);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setPreferredSize(new Dimension(30, 45));
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        colorPanel.add(colorChooser);
        JLabel colorText = new JLabel(text);
        colorText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        colorText.setPreferredSize(new Dimension(10,15));
        colorPanel.add(colorText);

        this.add(colorPanel);
    }

    private ActionListener action(ActionListener action){
        return e -> {
            buttons.forEach((button) -> button.setBackground(new JButton().getBackground()));

            JButton source = (JButton) e.getSource();
            source.setBackground(clickedColor);

            action.actionPerformed(e);
        };
    }

    private JButton buttonMaker(ActionListener action){
        JButton newButton = new JButton();
        newButton.addActionListener(action);
        newButton.setFocusPainted(false);
        newButton.setPreferredSize(new Dimension(35,35));
        buttons.add(newButton);
        return newButton;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner source = (JSpinner) e.getSource();
        currentShape.setStrokeWidth((Integer) source.getValue());
        // I was lazy so just did this switch instead of a good solution
        switch (currentShape.getMode()){
            case BRUSH -> currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth(),
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            case ERASER -> currentShape.setStroke(new BasicStroke(
                    currentShape.getStrokeWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            case RECTANGLE, CIRCLE, LINE -> currentShape.setStroke(new BasicStroke(currentShape.getStrokeWidth()));
        }
    }
}
