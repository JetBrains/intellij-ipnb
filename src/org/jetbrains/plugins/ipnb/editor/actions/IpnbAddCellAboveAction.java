package org.jetbrains.plugins.ipnb.editor.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.ipnb.editor.IpnbFileEditor;
import org.jetbrains.plugins.ipnb.editor.panels.IpnbFilePanel;
import org.jetbrains.plugins.ipnb.format.cells.IpnbCodeCell;

public class IpnbAddCellAboveAction extends AnAction {

  private final IpnbFileEditor myFileEditor;

  public IpnbAddCellAboveAction(IpnbFileEditor fileEditor) {
    super("Insert Cell Above");
    myFileEditor = fileEditor;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    final IpnbFilePanel component = myFileEditor.getIpnbFilePanel();
    addCell(component);
  }

  public void addCell(@NotNull final IpnbFilePanel ipnbFilePanel) {
    CommandProcessor.getInstance().executeCommand(ipnbFilePanel.getProject(), () -> ApplicationManager.getApplication().runWriteAction(
      () -> {
        ipnbFilePanel.createAndAddCell(false, IpnbCodeCell.createEmptyCodeCell());
        ipnbFilePanel.saveToFile(false);
      }), "Ipnb.createAndAddCell", new Object());

  }
}
