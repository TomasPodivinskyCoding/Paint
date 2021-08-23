package com.company.view.container;

import com.company.keybinds.control.ControlS;
import com.company.keybinds.control.ControlY;
import com.company.keybinds.control.ControlZ;
import com.company.keybinds.paintShortcuts.Template;
import com.company.view.container.paint.Paint;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.InputEvent;

public class Container extends JPanel {

    @Getter private final ControlY controlY;
    public Container(Paint paint, Toolbar toolbar){

        controlY = new ControlY(paint);
        createKeyBinding("CONTROL_Y", KeyStroke.getKeyStroke('Y',
                InputEvent.CTRL_DOWN_MASK), controlY);

        ControlZ controlZ = new ControlZ(paint, controlY);
        createKeyBinding("CONTROL_Z", KeyStroke.getKeyStroke('Z',
                InputEvent.CTRL_DOWN_MASK), controlZ);

        ControlS controlS = new ControlS(paint);
        createKeyBinding("CONTROL_S", KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK), controlS);

        Template p = new Template(toolbar, toolbar.getPencil());
        createKeyBinding("PENCIL", KeyStroke.getKeyStroke('p'), p);

        Template b = new Template(toolbar, toolbar.getBucket());
        createKeyBinding("BUCKET", KeyStroke.getKeyStroke('b'), b);

        Template e = new Template(toolbar, toolbar.getEraser());
        createKeyBinding("ERASER", KeyStroke.getKeyStroke('e'), e);

    }

    public void createKeyBinding(String name, KeyStroke keyStroke,Action action){
        this.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keyStroke, name);
        this.getActionMap().put(name, action);
    }



}
