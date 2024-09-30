package a01a.sol2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock
import org.mockito.Mockito.{spy, verify, times, when}
import java.util.Optional

class TestGUIExam extends AnyFlatSpec with Matchers:

  "A new GUI" should "have an empty text when created" in:
    val guiSize = 10
    val logics = spy(LogicImpl(guiSize, mock[Logger]))
    val guiProbe = GUIProbe(GUI(logics))
    for
      x <- 0 until guiSize
      y <- 0 until guiSize
    do
      val cellButton = guiProbe.buttonAt(Position(x, y))
      cellButton shouldBe defined
      cellButton.get.text shouldBe empty

  "A GUI" should "show the correct text when a cell is hit" in:
    val guiSize = 10
    val logics = spy(LogicImpl(guiSize, mock[Logger]))
    val cell = Position(0, 0)
    when(logics.hit(cell)).thenReturn(Optional.of(1))
    val guiProbe = GUIProbe(GUI(logics))
    val cellButton = guiProbe.buttonAt(cell)
    cellButton shouldBe defined
    cellButton.get.click()
    cellButton.get.text shouldBe "1"
