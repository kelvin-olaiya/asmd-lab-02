package a01a.sol2;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogicImpl implements Logic {

    private final int size;
    private List<Position> marks = new LinkedList<>();
    private boolean moving = false;
    private final Logger logger;

    public LogicImpl(int size, Logger logger) {
        this.size = size;
        this.logger = logger;
    }

    @Override
    public Optional<Integer> hit(Position position) {
        if (this.isOver()){
            this.logger.log("Game is over");
            return Optional.empty();
        }
        if (this.moving || startMoving(position)){
            if (!this.moving && startMoving(position)) {
                this.logger.log("Cell in " + position + " hit and adjacent to a numbered cell");
            }
            this.moving = true;
            this.moveMarks();
            return Optional.empty();
        }
        this.marks.add(position);
        this.logger.log("Cell in " + position + " hit and labelled with " + this.marks.size());
        return Optional.of(this.marks.size());
    }

    private boolean neighbours(Position p1, Position p2){
        return Math.abs(p1.x()-p2.x()) <= 1 && Math.abs(p1.y()-p2.y()) <= 1;
    }

    private boolean startMoving(Position position) {
        return this.marks.stream().anyMatch(p -> neighbours(p, position));
    }

    private void moveMarks() {
        this.marks = this.marks
                .stream()
                .peek(p -> this.logger.log("Moving cell from " + p + " to " + moveUpRight(p)))
                .map(p -> moveUpRight(p))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private Position moveUpRight(Position p) {
        return new Position(p.x()+1, p.y()-1);
    }

    @Override
    public Optional<Integer> getMark(Position position) {
        return Optional.of(this.marks.indexOf(position)).filter(i -> i>=0).map(i -> i+1);
    }

    @Override
    public boolean isOver() {
        return this.marks.stream().anyMatch(p -> p.x() == this.size || p.y() == -1);
    }
}
