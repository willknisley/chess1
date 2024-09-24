package chess;

import java.util.*;

import static chess.ChessPiece.PieceType.BISHOP;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor color;
    private final PieceType type;

public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    public abstract class Rules implements MovementRule{
        static private final HashMap<PieceType, MovementRule> rules = new HashMap<>();

        {
            //rules.put(PieceType.KING, new KingMovementRule());
            //rules.put(PieceType.QUEEN, new QueenMovementRule());
            rules.put(PieceType.KNIGHT, new KnightMovementRule());
            rules.put(PieceType.BISHOP, new BishopMovementRule());
            rules.put(PieceType.ROOK, new RookMovementRule());
            //rules.put(PieceType.PAWN, new PawnMovementRule());
        }

        static public MovementRule pieceRule(PieceType type) {
            return rules.get(type);
        }

    }



    public abstract class BaseMovementRule implements MovementRule {
        protected void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc,
                                      Collection<ChessMove> moves, boolean allowDistance){

        }
    }

    public interface MovementRule {
        Collection<ChessMove> moves(ChessBoard board, ChessPosition pos);

        //MovementRule rule = Rules.pieceRule(this.type);
    }
        /*
        Rule rule = switch (getPieceType()) {
            case BISHOP -> new Rule(true, new int[][]{{1, -1}, {-1, 1}, {-1, -1}, {1, 1}});
            case ROOK   -> new Rule(true, new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}});
            case KNIGHT -> new Rule(false, new int[][]{{2, 1}, {2, -1}, {-2, 1}});
            case QUEEN  -> new Rule(true, new int[][]{{1, -1}, {-1, 1}, {-1, -1}, {1, 1}});
            case KING   -> new Rule(false, new int[][]{{1, -1}, {-1, 1}, {-1, -1}, {1, 1}});
            default -> null;
        };

        return rule.getMoves(board, myPosition);
    }*/

    public class BishopMovementRule extends BaseMovementRule {
        @Override
        public Collection<ChessMove> moves(ChessBoard board, ChessPosition position) {
            var moves = new HashSet<ChessMove>();
            calculateMoves(board, position, -1, -1, moves, true);
            calculateMoves(board, position, 1, -1, moves, true);
            calculateMoves(board, position, -1, 1, moves, true);
            calculateMoves(board, position, 1, 1, moves, true);
            return moves;
        }
    }

    public class RookMovementRule extends BaseMovementRule {
        @Override
        public Collection<ChessMove> moves(ChessBoard board, ChessPosition position) {
            var moves = new HashSet<ChessMove>();
            calculateMoves(board, position, -1, 0, moves, true);
            calculateMoves(board, position, 1, 0, moves, true);
            calculateMoves(board, position, 0, -1, moves, true);
            calculateMoves(board, position, 0, 1, moves, true);
            return moves;
        }
    }

    public class KnightMovementRule extends BaseMovementRule {
        @Override
        public Collection<ChessMove> moves(ChessBoard board, ChessPosition position) {
            var moves = new HashSet<ChessMove>();
            calculateMoves(board, position, 1, -2, moves, true);
            calculateMoves(board, position, -1, -2, moves, true);
            calculateMoves(board, position, 1, 2, moves, true);
            calculateMoves(board, position, -1, 2, moves, true);
            return moves;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}

