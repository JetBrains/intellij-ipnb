package org.jetbrains.plugins.ipnb.format.cells;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.ipnb.format.cells.output.IpnbOutputCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IpnbCodeCell extends IpnbEditableCell {
  @NotNull private final String myLanguage;
  @Nullable private Integer myPromptNumber;
  @NotNull private final List<IpnbOutputCell> myCellOutputs;

  public IpnbCodeCell(@NotNull final String language,
                      @NotNull final String[] input,
                      @Nullable final Integer number,
                      @NotNull final List<IpnbOutputCell> cellOutputs) {
    super(input);
    myLanguage = language;
    myPromptNumber = number;
    myCellOutputs = cellOutputs;
  }

  @NotNull
  public String getLanguage() {
    return myLanguage;
  }

  @Nullable
  public Integer getPromptNumber() {
    return myPromptNumber;
  }

  public void setPromptNumber(@Nullable Integer promptNumber) {
    myPromptNumber = promptNumber;
  }

  @NotNull
  public List<IpnbOutputCell> getCellOutputs() {
    return myCellOutputs;
  }

  public void removeCellOutputs() {
    myCellOutputs.clear();
  }

  public void addCellOutput(@NotNull final IpnbOutputCell cellOutput) {
    myCellOutputs.add(cellOutput);
  }

  @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
  @Override
  public Object clone() {
    return new IpnbCodeCell(myLanguage, Arrays.copyOf(getSource(), getSource().length), myPromptNumber, new ArrayList<IpnbOutputCell>(myCellOutputs));
  }
}
