package org.jetbrains.plugins.ipnb.editor.panels;

import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.ipnb.format.cells.IpnbCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class IpnbEditablePanel<T extends JComponent, K extends IpnbCell> extends IpnbPanel<T, K> {
  private boolean myEditing;
  protected JTextArea myEditablePanel;
  public final static String EDITABLE_PANEL = "Editable panel";
  public final static String VIEW_PANEL = "View panel";

  public IpnbEditablePanel(@NotNull K cell) {
    super(cell);
  }


  protected void initPanel() {
    myViewPanel = createViewPanel();
    myViewPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        final Container parent = getParent();
        final MouseEvent parentEvent = SwingUtilities.convertMouseEvent(myViewPanel, e, parent);
        parent.dispatchEvent(parentEvent);
        if (e.getClickCount() == 2) {
          switchToEditing();
        }
      }
    });

    add(myViewPanel, VIEW_PANEL);
    myEditablePanel = createEditablePanel();
    add(myEditablePanel, EDITABLE_PANEL);
  }

  public void switchToEditing() {
    setEditing(true);

    final LayoutManager layout = getLayout();
    if (layout instanceof CardLayout) {
      ((CardLayout)layout).show(this, EDITABLE_PANEL);
      UIUtil.requestFocus(myEditablePanel);
    }
  }

  protected String getRawCellText() { return ""; }

  public void runCell() {
    final LayoutManager layout = getLayout();
    if (layout instanceof CardLayout) {
      updateCellView();
      ((CardLayout)layout).show(this, VIEW_PANEL);
      setEditing(false);
    }
  }

  private JTextArea createEditablePanel() {
    final JTextArea textArea = new JTextArea(getRawCellText());
    textArea.setLineWrap(true);
    textArea.setEditable(true);
    textArea.setBorder(BorderFactory.createLineBorder(JBColor.lightGray));
    textArea.setBackground(Gray._247);
    textArea.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
          setEditing(true);
          final Container parent = getParent();
          parent.repaint();
          if (parent instanceof IpnbFilePanel) {
            ((IpnbFilePanel)parent).setSelectedCell(IpnbEditablePanel.this);
            textArea.requestFocus();
          }
        }
      }
    });
    textArea.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          setEditing(false);
          getParent().repaint();
          requestFocus();
        }
      }
    });
    return textArea;
  }

  public boolean contains(int y) {
    return y>= getTop() && y<=getBottom();
  }

  public int getTop() {
    return getY();
  }

  public int getBottom() {
    return getTop() + getHeight();
  }

  public boolean isEditing() {
    return myEditing;
  }

  public void setEditing(boolean editing) {
    myEditing = editing;
  }

  public void updateCellView() { // TODO: make abstract
  }

}