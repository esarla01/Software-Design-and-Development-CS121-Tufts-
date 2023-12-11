import java.util.*;

public class Queen extends Piece {
    public Queen(Color c) { pieceColor = c; }
    // implement appropriate methods

    public String toString() {
        if (pieceColor.equals(Color.WHITE)) {
            return "wq";
        }
        return "bq";
    }

    public List<String> moves(Board b, String loc) {
        List<String> pMoves = new LinkedList<String>();
        linearMovement(b, pMoves, loc);
        diagonalMovement(b, pMoves, loc);
        return pMoves;
    }


     //get the spot for the given col and row
     private String getSpot(int col, int row) {
        return Character.toString(col + 97) + Integer.toString(row + 1);
    }

    //check if the col and row are within the boundaries of the chess table
    private boolean checkBounds(int col, int row) {
        if (col >= 0 && col <= 7) { // check if column is valid
            
            if (row >= 0 && row <= 7) { // check if row is valid
                return true;
            }
        }
        return false; 
    }

    //---------------------------------------------------------- movement of Rook ----------------------------------------------------------/
    //find new spot on a linear axis
    private String moveSpotLinearly(int col, int row, int bCol, int bRow, int i) {
        if (bCol == 0 && bRow == 1) {
            return getSpot(col, row + i);
        } else if (bCol == 0 && bRow == -1) {
            return getSpot(col, row - i);
        } else if (bCol == 1 && bRow == 0) {
            return getSpot(col + i, row );
        } else {
            return getSpot(col - i, row);
        }
    }

    //move helper for linear movement
    private void movesHelperLinear(Board b, List<String> pMoves, String loc, Color pColor,
                                                                int bCol, int bRow) {
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;

        for (int i = 1; i < 8; i ++) {
            String newSpot = moveSpotLinearly(col, row, bCol, bRow, i);
            if (!checkBounds(col + (bCol * i), row + (bRow * i))) {
                break;
            }
            if (b.getPiece(newSpot) == null) {
                pMoves.add(newSpot);
            } else {
                if (b.getPiece(newSpot).color() ==  pColor) {
                    pMoves.add(newSpot);
                }
                break;
            }
        }     
    }

    //queen's linear movement
    private void linearMovement(Board b, List<String> pMoves, String loc) {
        if (pieceColor == Color.WHITE) {
            movesHelperLinear(b, pMoves, loc, Color.BLACK, 1, 0);
            movesHelperLinear(b, pMoves, loc, Color.BLACK, -1, 0);
            movesHelperLinear(b, pMoves, loc, Color.BLACK, 0, 1);
            movesHelperLinear(b, pMoves, loc, Color.BLACK, 0, -1);
        } else {
            movesHelperLinear(b, pMoves, loc, Color.WHITE, 1, 0);
            movesHelperLinear(b, pMoves, loc, Color.WHITE, -1, 0);
            movesHelperLinear(b, pMoves, loc, Color.WHITE, 0, 1);
            movesHelperLinear(b, pMoves, loc, Color.WHITE, 0, -1);
        }
    }



    //---------------------------------------------------------- movement of Bishop ----------------------------------------------------------/
    //find new spot on a diagonal axis
    private String moveSpotDiagonally(int col, int row, int bCol, int bRow, int i) {
        if (bCol == 1 && bRow == 1) {
            return getSpot(col + i, row + i);
        } else if (bCol == 1 && bRow == -1) {
            return getSpot(col + i, row - i);
        } else if (bCol == -1 && bRow == -1) {
            return getSpot(col - i, row - i);
        } else {
            return getSpot(col - i, row  + i);
        }
    }

    //move helper for diagonal movement
    private void movesHelperDiagonal(Board b, List<String> pMoves, String loc, Color cOpponent,
                                                         int bCol, int bRow) {
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;
        for (int i = 1; i < 8; i ++) {           
            String newSpot = moveSpotDiagonally(col, row, bCol, bRow, i);
            if (!checkBounds(col + (bCol * i), row + (bRow * i))) {
                break;
            }
            if (b.getPiece(newSpot) == null) {
                pMoves.add(newSpot);
            } else {
                if (b.getPiece(newSpot).color() ==  cOpponent) {
                    pMoves.add(newSpot);
                }
                break;
            }
        }
    }

    //queen's diagonal movement
    private void diagonalMovement(Board b, List<String> pMoves, String loc) {
        if (pieceColor == Color.WHITE) {
            movesHelperDiagonal(b, pMoves, loc, Color.BLACK, 1, 1);
            movesHelperDiagonal(b, pMoves, loc, Color.BLACK, 1, -1);
            movesHelperDiagonal(b, pMoves, loc, Color.BLACK, -1, 1);
            movesHelperDiagonal(b, pMoves, loc, Color.BLACK, -1, -1);
        } else {
            movesHelperDiagonal(b, pMoves, loc, Color.WHITE, 1, 1);
            movesHelperDiagonal(b, pMoves, loc, Color.WHITE, 1, -1);
            movesHelperDiagonal(b, pMoves, loc, Color.WHITE, -1, 1);
            movesHelperDiagonal(b, pMoves, loc, Color.WHITE, -1, -1);
        }
    }
}
