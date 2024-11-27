import java.util.*;
import java.nio.*;
import java.io.*;

public class Rook extends Piece {
    public Rook(Color c) { pieceColor = c; }
    // implement appropriate methods

    public String toString() {
	    if (pieceColor.equals(Color.WHITE)) {
            return "wr";
        }
        return "br";
    }

    public List<String> moves(Board b, String loc) {
        List<String> pMoves = new LinkedList<String>();
            if (pieceColor == Color.WHITE) {
                movesHelper(b, pMoves, loc, Color.BLACK, 1, 0);
                movesHelper(b, pMoves, loc, Color.BLACK, -1, 0);
                movesHelper(b, pMoves, loc, Color.BLACK, 0, 1);
                movesHelper(b, pMoves, loc, Color.BLACK, 0, -1);
            } else {
                movesHelper(b, pMoves, loc, Color.WHITE, 1, 0);
                movesHelper(b, pMoves, loc, Color.WHITE, -1, 0);
                movesHelper(b, pMoves, loc, Color.WHITE, 0, 1);
                movesHelper(b, pMoves, loc, Color.WHITE, 0, -1);
            }
            return pMoves;
        
    }

    //get the spot for the given col and row
    public String getSpot(int col, int row) {
        return Character.toString(col + 97) + Integer.toString(row + 1);
    }

    //check if the col and row are within the boundaries of the chess table
    public boolean checkBounds(int col, int row) {
        if (col >= 0 && col <= 7) { // check if column is valid
            
            if (row >= 0 && row <= 7) { // check if row is valid
                return true;
            }
        }
        return false; 
    }

    public String moveSpot(int col, int row, int bCol, int bRow, int i) {
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

    public void movesHelper(Board b, List<String> pMoves, String loc, Color pColor,
                                                                int bCol, int bRow) {
        //determine the column and row values
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;

        for (int i = 1; i < 8; i ++) {
            System.out.println(checkBounds(col + (bCol * i), row + (bRow * i)));
            String newSpot = moveSpot(col, row, bCol, bRow, i);
            System.out.println(newSpot);

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

}