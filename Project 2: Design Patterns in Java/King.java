import java.util.*;

public class King extends Piece {
    public King(Color c) { pieceColor = c; }
    // implement appropriate methods

    public String toString() {
        if (pieceColor.equals(Color.WHITE)) {
            return "wk";
        }
        return "bk";
    }

    public List<String> moves(Board b, String loc) {
        List<String> pMoves = new LinkedList<String>();
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;

        
        //case 1,  row: + 1 col: - 1, up-left diagonal
        String newSpot1 = getSpot(col - 1, row + 1);
        if (checkNull(b, newSpot1) && checkBounds(col - 1, row + 1)) {
            System.out.println("up-left-diagonal");
            System.out.println(newSpot1);
            pMoves.add(newSpot1);
        }

        //case 2, row + 1 col: 0 , up
        String newSpot2 = getSpot(col, row + 1);
        if (checkNull(b, newSpot2) && checkBounds(col, row + 1)) {
            System.out.println("up");
            System.out.println(newSpot2);
            pMoves.add(newSpot2);
        }
       
        //case 3, row + 1 col: + 1, up-right diagonal
        String newSpot3 = getSpot(col + 1, row + 1);
        if (checkNull(b, newSpot3) && checkBounds(col + 1, row + 1)) {
            System.out.println("up-right-diagonal");
            System.out.println(newSpot3);
            pMoves.add(newSpot3);
        }
 
        //case 4, row - 1 col: 0, left
        String newSpot4 = getSpot(col - 1, row);
        if (checkNull(b, newSpot4) && checkBounds(col - 1, row)) {
            System.out.println("left");
            System.out.println(newSpot4);
            pMoves.add(newSpot4);
        }
 
        //case 5:, row: 0 col: + 1, right
        String newSpot5 = getSpot(col + 1, row);
        if (checkNull(b, newSpot5) && checkBounds(col + 1, row)) {
            System.out.println("right");
            System.out.println(newSpot5);
            pMoves.add(newSpot5);
        }
 
        //case 6: row - 1 col: - 1 , down-left diagonal
        String newSpot6 = getSpot(col - 1, row - 1);
        if (checkNull(b, newSpot6) && checkBounds(col - 1, row - 1)) {
            System.out.println("down-left-diagonal");
            System.out.println(newSpot6);
            pMoves.add(newSpot6);
        }
 
        //case 7: row - 1 col: 0, down
        String newSpot7 = getSpot(col, row - 1);
        if (checkNull(b, newSpot7) && checkBounds(col, row - 1)) {
            System.out.println("down");
            System.out.println(newSpot7);
            pMoves.add(newSpot7);
        }
 
        //case 8: row - 1 col: - 1, down-right diagonal
        String newSpot8 = getSpot(col + 1, row - 1);
        if (checkNull(b, newSpot8) && checkBounds(col + 1, row - 1)) {
            System.out.println("down-right-diagonal");
            System.out.println(newSpot8);
            pMoves.add(newSpot8);
        }

        return  pMoves;
    }


    private boolean checkNull(Board b, String newSpot){
        if (b.getPiece(newSpot) == null) { //|| b.getPiece(newSpot).color() == pieceColor 
            return true;
        }
        return false;
    }

    private boolean checkBounds(int col, int row) {
        if (col >= 0 && col <= 7) { // check if column is valid
            
            if (row >= 0 && row <= 7) { // check if row is valid
                return true;
            }
        }
        return false; 
    }

    private String getSpot(int col, int row) {
        return Character.toString(col + 97) + Integer.toString(row + 1);
    }

}