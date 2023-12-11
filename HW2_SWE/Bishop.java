import java.util.*;

public class Bishop extends Piece {
    public Bishop(Color c) { pieceColor = c; }
    // implement appropriate methods

    public String toString() {
        if (pieceColor.equals(Color.WHITE)) {
            return "wb";
        }
        return "bb";
    }

    public List<String> moves(Board b, String loc) {
        List<String> pMoves = new LinkedList<String>();
            if (pieceColor == Color.WHITE) {
                movesHelper(b, pMoves, loc, Color.BLACK, 1, 1);
                movesHelper(b, pMoves, loc, Color.BLACK, 1, -1);
                movesHelper(b, pMoves, loc, Color.BLACK, -1, 1);
                movesHelper(b, pMoves, loc, Color.BLACK, -1, -1);
            } else {
                movesHelper(b, pMoves, loc, Color.WHITE, 1, 1);
                movesHelper(b, pMoves, loc, Color.WHITE, 1, -1);
                movesHelper(b, pMoves, loc, Color.WHITE, -1, 1);
                movesHelper(b, pMoves, loc, Color.WHITE, -1, -1);
            }
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


    private void movesHelper(Board b, List<String> pMoves, String loc, Color cOpponent,
                                                         int bCol, int bRow) {
        //determine the column and row values
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;

        for (int i = 1; i < 8 ; i ++) {
            String newSpot = moveSpot(col, row, bCol, bRow, i);

            if (!checkBounds(col + (bCol * i), row + (bRow * i))) {
                break;
            }
            //System.out.println("New spot: " + newSpot);

            if (b.getPiece(newSpot) == null) {
                //System.out.println("Is it null: " + b.getPiece(newSpot));
                pMoves.add(newSpot);
            } else {
                if (b.getPiece(newSpot).color() ==  cOpponent) {
                    //System.out.println("Opponent encountered: " + newSpot);
                    pMoves.add(newSpot);
                }
                //System.out.println("break");
                break;
                }
        }
    }

    private String moveSpot(int col, int row, int bCol, int bRow, int i) {
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

}

