package a01a.sol2;

import java.util.Optional;

public interface Logic {

    int getSize();

    Optional<Integer> hit(Position position);

    Optional<Integer> getMark(Position position);

    boolean isOver();
}
