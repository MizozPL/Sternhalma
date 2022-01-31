package com.sternhalma.replay.gui;

import javax.swing.*;
import java.awt.*;

public class ReplayFrame extends JFrame{

        private JPanel replayPanel = null;

        public ReplayFrame() {
            setTitle("REPLAY");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        public void setReplayPanel(JPanel panel) {
            if (replayPanel != null)
                remove(replayPanel);
            replayPanel = panel;
            add(replayPanel, BorderLayout.CENTER);
            revalidate();
        }
}
