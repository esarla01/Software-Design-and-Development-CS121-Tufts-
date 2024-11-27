import java.util.*;

public class Knight extends Piece {
    public Knight(Color c) { pieceColor = c; }
    // implement appropriate methods

    public String toString() {
        if (pieceColor.equals(Color.WHITE)) {
            return "wn";
        }
        return "bn";
    }

    public List<String> moves(Board b, String loc) {
        List<String> pMoves = new LinkedList<String>();

        if (pieceColor == Color.WHITE) {
            movesHelper(pMoves, b,  loc, Color.BLACK);
        } else {
            movesHelper(pMoves, b,  loc, Color.WHITE);
        }     
        return pMoves; 
    }

    //get the spot for the given col and row
    private String getSpot(int col, int row) {
        return Character.toString(col + 97) + Integer.toString(row + 1);
    }

    //check if the given col and row are within the bounds of the chess table
    private boolean checkBounds(int col, int row) {
        if (col >= 0 && col <= 7) { // check if column is valid
            if (row >= 0 && row <= 7) { // check if row is valid
                return true;
            }
        }
        return false; 
    }

    private void movesHelper(List<String> pMoves, Board b, String loc , Color c) {
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;

        //case 1: upwards-right
        if (checkBounds(col + 1, row + 2)) {
            if (b.getPiece(getSpot(col + 1, row + 2)) == null || 
                        b.getPiece(getSpot(col + 1, row + 2)).color() == c) {
                pMoves.add(getSpot(col + 1, row + 2));
            }
        }
        //case 2: upwards-left
        if (checkBounds(col - 1, row + 2)) {
            if (b.getPiece(getSpot(col - 1, row + 2)) == null || 
                        b.getPiece(getSpot(col - 1, row + 2)).color() == c) {
                pMoves.add(getSpot(col - 1, row + 2));
            }
        }
        //case 3: downwards-right
        if (checkBounds(col + 1, row - 2)) {
            if (b.getPiece(getSpot(col + 1, row - 2)) == null || 
                        b.getPiece(getSpot(col + 1, row - 2)).color() == c) {
                pMoves.add(getSpot(col + 1, row - 2));
            }
        }

        //case 4: downwards-left
        if (checkBounds(col - 1, row - 2)) {
            if (b.getPiece(getSpot(col - 1, row - 2)) == null || 
                        b.getPiece(getSpot(col - 1, row - 2)).color() == c) {
                pMoves.add(getSpot(col - 1, row - 2));
            }
        }

        //case 5: right-up
        if (checkBounds(col + 2, row + 1)) {
            if (b.getPiece(getSpot(col + 2, row + 1)) == null || 
                        b.getPiece(getSpot(col + 2, row + 1)).color() == c) {
                pMoves.add(getSpot(col + 2, row + 1));
            }
        }

        //case 6: right-down
        if (checkBounds(col + 2, row - 1)) {
            if (b.getPiece(getSpot(col + 2, row - 1)) == null || 
                        b.getPiece(getSpot(col + 2, row - 1)).color() == c) {
                pMoves.add(getSpot(col + 2, row - 1));
            }
        }

        //case 7: left-up
        if (checkBounds(col - 2, row + 1)) {
            if (b.getPiece(getSpot(col - 2, row + 1)) == null || 
                        b.getPiece(getSpot(col - 2, row + 1)).color() == c) {
                pMoves.add(getSpot(col - 2, row + 1));
            }

        }

        //case 8: left-down
        if (checkBounds(col - 2, row - 1)) {
            if (b.getPiece(getSpot(col - 2, row - 1)) == null || 
                        b.getPiece(getSpot(col - 2, row - 1)).color() == c) {
                pMoves.add(getSpot(col - 2, row - 1));
            }
        }

    }

}




