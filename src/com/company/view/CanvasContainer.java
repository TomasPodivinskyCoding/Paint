package com.company.view;

import com.company.keybinds.ControlY;
import com.company.keybinds.ControlZ;

import javax.swing.*;
import java.awt.event.InputEvent;

public class CanvasContainer extends JPanel {

    public CanvasContainer(Paint paint){
        ControlY controlY = new ControlY(paint);
        final String CONTROL_Y_NAME = "controlY";
        // control Y bind
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('Y',
                InputEvent.CTRL_DOWN_MASK), CONTROL_Y_NAME);
        this.getActionMap().put(CONTROL_Y_NAME, controlY);

        final String CONTROL_Z_NAME = "controlZ";
        // control Z bind
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke('Z',
                InputEvent.CTRL_DOWN_MASK), CONTROL_Z_NAME);
        this.getActionMap().put(CONTROL_Z_NAME, new ControlZ(paint, controlY));

    }

}
