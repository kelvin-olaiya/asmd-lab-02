package a01a.sol2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock
import org.mockito.Mockito.{spy, verify, times}
import org.junit.jupiter.api.BeforeEach

class TestGUIExamLogger extends AnyFlatSpec with Matchers:
  val labelledCellsPositions = List(
    Position(0, 0),
    Position(2, 0),
    Position(1, 2),
    Position(3, 2),
    Position(1, 4)
  )
  val adjacentCellPosition = Position(2, 1)

  "A message" should "be logged when a cell is hit" in:
    val loggerMock = mock[Logger]
    val logics = LogicImpl(10, loggerMock)
    for (cell, idx) <- labelledCellsPositions.zipWithIndex do
      logics.hit(cell)
      verify(loggerMock).log(
        s"Cell in $cell hit and labelled with ${idx + 1}"
      )

  it should "be logged when a cell adjacent to a numbered cell is hit" in:
    val loggerMock = mock[Logger]
    val logics = LogicImpl(10, loggerMock)
    labelledCellsPositions.foreach(logics.hit)
    logics.hit(adjacentCellPosition)
    verify(loggerMock).log(
      s"Cell in $adjacentCellPosition hit and adjacent to a numbered cell"
    )

  it should "be logged when a cell is moved" in:
    val loggerMock = mock[Logger]
    val logics = LogicImpl(10, loggerMock)
    (labelledCellsPositions :+ adjacentCellPosition).foreach(logics.hit)
    labelledCellsPositions.foreach: p =>
      verify(loggerMock).log(
        s"Moving cell from $p to ${Position(p.x + 1, p.y - 1)}"
      )
  it should "be logged when a the game quits" in:
    val loggerMock = mock[Logger]
    val logics = LogicImpl(10, loggerMock)
    (labelledCellsPositions :+ adjacentCellPosition).foreach(logics.hit)
    logics.isOver() shouldBe true
    logics.hit(Position(0, 0))
    verify(loggerMock).log("Game is over")
