package chess;

import java.util.Collection;

public abstract class MovementRule {
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos);
}
