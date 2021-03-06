package com.company.view.container.paint;

import com.company.program_controlls.keybinds.control.ControlY;
import com.company.program_controlls.mouse_listeners.PaintMouseListeners;
import com.company.program_controlls.mouse_listeners.ZoomManager;
import com.company.shapeMaker.ShapeContainer;
import com.company.shapeMaker.ShapeMaker;
import com.company.shapeMaker.ShapeModes;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/*
* Is displayed on the frame!
* Together with ShapeMaker controls the drawing
* */
public class Paint extends JPanel {

    private final ShapeMaker shapeMaker;

    @Getter @Setter private PaintContainer paintC;

    @Getter @Setter double scale = 1;

    @Getter private final ZoomManager zoomManager;

    @Getter @Setter private Dimension currentSize = new Dimension(640,480);

    @Getter PaintMouseListeners mouse;

    public Paint(ShapeMaker currentShape){
        this.shapeMaker = currentShape;

        Dimension defaultSize = currentSize;

        this.setSize(defaultSize);

        mouse = new PaintMouseListeners(shapeMaker, this);

        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.zoomManager = new ZoomManager(this);
        this.addMouseWheelListener(zoomManager);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;

        gd.setColor(Color.white);
        gd.fillRect(0, 0, getWidth(), getHeight());

        gd.scale(scale, scale);

        Stroke defaultStroke = gd.getStroke();

        mouse.getToPaint().forEach((painting)->{
            if (painting.getShapes() != null){
                gd.setColor(painting.getColor());
                gd.setStroke(painting.getStroke());

                painting.getShapes().forEach((paint -> gd.draw(paint.getShape())));

                gd.setStroke(defaultStroke);
            }else{
                gd.setColor(painting.getColor());
                switch (painting.getPaintType()){
                    case DRAW -> {
                        gd.draw(painting.getShape());
                        gd.setStroke(painting.getStroke());
                    }
                    case IMAGE -> gd.drawImage(painting.getImg(), 0, 0, null);
                }
            }
        });


        gd.dispose();
    }

    public void addToLoadedImages(BufferedImage img){
        mouse.getToPaint().add(new ShapeContainer(shapeMaker.getColor(), img, PaintType.IMAGE));
    }

    public void setCtrlY(ControlY ctrlY){
        this.mouse.setCtrlY(ctrlY);
    }

}
