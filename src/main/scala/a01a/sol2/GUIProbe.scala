package a01a.sol2

import javax.swing.JButton
import scala.jdk.OptionConverters._

case class CellButton(
    private val position: Position,
    private val button: JButton
):
  def click(): Unit = button.doClick()
  def text: String = button.getText()

class GUIProbe(gui: GUI):
  gui.setVisible(false)
  def buttonAt(position: Position): Option[CellButton] =
    gui.cells
      .entrySet()
      .stream()
      .filter(_.getValue() == position)
      .map(_.getKey())
      .findFirst()
      .toScala
      .map(CellButton(position, _))
