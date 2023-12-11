import java.util.*;
public class Pawn extends Piece {
    public Pawn(Color c) { pieceColor = c; }
    // implement appropriate methods

    public String toString() {
	    if (pieceColor == Color.WHITE) {
            return "wp";
        }
        return "bp";
    }

    public List<String> moves(Board b, String loc) {
        List<String> pMoves = new LinkedList<String>();
        int col = loc.charAt(0) - 97;
        int row = Character.getNumericValue(loc.charAt(1)) - 1;
        //case 1: move two forward at home
        if (isHome(row)) {
            if (pieceColor == Color.WHITE) {
                if (b.getPiece(getSpot(col, row + 1)) == null && b.getPiece(getSpot(col, row + 2)) == null) {
                    pMoves.add(getSpot(col, row + 2));
                }
            }  
            else if (pieceColor == Color.BLACK) {
                if (b.getPiece(getSpot(col, row - 1)) == null && b.getPiece(getSpot(col, row - 2)) == null) {
                    pMoves.add(getSpot(col, row - 2));
                }
            }

        }

        
        //case 2: move one forward
        if (pieceColor == Color.WHITE) {
            if (checkRow(row + 1)) {
                if (b.getPiece(getSpot(col, row + 1)) == null) {
                    pMoves.add(getSpot(col, row + 1));
                }
            }
        }
        else if (pieceColor == Color.BLACK)  {
                if (checkRow(row - 1)) {
                    if (b.getPiece(getSpot(col, row - 1)) == null) {
                        pMoves.add(getSpot(col, row - 1));
                    }
                }
        }
    
            //case 3: move diagonal to capture opponent
        if (pieceColor == Color.WHITE) {
            //check up-left diagonal
            if (checkForOpponent(b, col - 1, row + 1)) {
                pMoves.add(getSpot(col - 1, row + 1));
            }

            //color up-right diagonal
            if (checkForOpponent(b, col + 1, row + 1)) {
                pMoves.add(getSpot(col + 1, row + 1));
            }

        } else if (color() == Color.BLACK) {
            //check down-left diagonal
            if (checkForOpponent(b, col - 1, row - 1)) {
                pMoves.add(getSpot(col - 1, row - 1));
            }

            //check down-right diagonal
            if (checkForOpponent(b, col + 1, row - 1)) {
                pMoves.add(getSpot(col + 1, row - 1));
            }

        }
            return pMoves;
        }



        //get the spot for the given col and row
        private String getSpot(int col, int row) {
            return Character.toString(col + 97) + Integer.toString(row + 1);
        }

        //check if the pawns are at their home 
        private boolean isHome(int row) {
            if (pieceColor == Color.WHITE) {
                if (row == 1) {
                    return true;
                }
                return false;
            }
            else {
                if (row == 6) {
                    return true;
                }
                return false;
            }
        }

        //check if one move forward is on the table
        private boolean checkRow(int row) {
            if (row >= 1 && row <= 7) {
                return true;
            }
            return false;
        }
        
        //check if there is an opponent on the diagonal tile
        private boolean checkForOpponent(Board b, int col, int row) {
            if (pieceColor == Color.WHITE) {
                if (b.getPiece(getSpot(col, row)) != null 
                        && b.getPiece(getSpot(col, row)).color() == Color.BLACK){
                            System.out.println("true: white");
                            return true;
                }
                return false;
            } else {
                if (b.getPiece(getSpot(col, row)) != null 
                        && b.getPiece(getSpot(col, row)).color() == Color.WHITE){
                            System.out.println("true: black");
                            return true;
                }
                return false;
            }
        }

        

    }

