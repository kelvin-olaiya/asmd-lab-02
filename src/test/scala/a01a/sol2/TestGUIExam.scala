package a01a.sol2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock
import org.mockito.Mockito.{spy, verify, times, when}
import org.mockito.ArgumentMatchers.{any}
import java.util.Optional
import scala.compiletime.ops.double
import scala.util.Random
import org.scalatest.Ignore

class TestGUIExam extends AnyFlatSpec with Matchers:

  private val guiSize = 10

  "A new GUI" should "have an empty text on all cells when created" in:
    assume(System.getenv().get("CI") != "true")
    val logics = spy(LogicImpl(guiSize, mock[Logger]))
    val guiProbe = GUIProbe(GUI(logics))
    for
      x <- 0 until guiSize
      y <- 0 until guiSize
    do
      val cellButton = guiProbe.buttonAt(Position(x, y))
      cellButton shouldBe defined
      cellButton.get.text shouldBe empty

  "A cell button on the GUI" should "show the correct text when a cell is hit" in:
    assume(System.getenv().get("CI") != "true")
    val numberOfClicks = 10
    val cells = Set.fill(numberOfClicks)(
      Position(Random.nextInt(guiSize), Random.nextInt(guiSize))
    )
    val clickResults = LazyList.fill(numberOfClicks)(Random.nextInt)
    val test = cells.zip(clickResults)
    val logics = mock[Logic]
    when(logics.getSize()).thenReturn(guiSize)
    when(logics.isOver()).thenReturn(false)
    test.foreach: (c, r) =>
      when(logics.getMark(c)).thenReturn(Optional.of(r))
    val guiProbe = GUIProbe(GUI(logics))
    for (cell, result) <- test do
      val cellButton = guiProbe.buttonAt(cell)
      cellButton shouldBe defined
      cellButton.get.click()
      cellButton.get.text shouldBe result.toString
