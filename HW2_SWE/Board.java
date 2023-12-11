import java.util.*;

public class Board {

    final private static Board theBoard = new Board();

    private Piece[][] pieces = new Piece[8][8];

    private List<BoardListener> listenerList = new LinkedList<BoardListener>();

    private Board() { }
    
    public static Board theBoard() {
        return theBoard;
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
        try {
            checkBounds(loc);
        }  
        catch (Exception e){

        }    
        return pieces[loc.charAt(0) - 97][Character.getNumericValue(loc.charAt(1)) - 1];
        
    }

    public void addPiece(Piece p, String loc) {
        
        checkNull(loc);
        checkBounds(loc);
        pieces[loc.charAt(0) - 97][Character.getNumericValue(loc.charAt(1)) -1] = p; 
            
    
    }

    private void checkNull(String loc) {
        if (pieces[loc.charAt(0) - 97][Character.getNumericValue(loc.charAt(1))-1] != null) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void checkBounds(String loc) {
        if (!(loc.charAt(0) - 97 >= 0 || loc.charAt(0) - 97 <= 7)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        else if (!(Character.getNumericValue(loc.charAt(1)) >= 1 || Character.getNumericValue(loc.charAt(1)) <= 8)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void movePiece(String from, String to) {
        int colFrom = from.charAt(0) - 97;
        int rowFrom = Character.getNumericValue(from.charAt(1))-1;

        //try {
            if (isThereChessPiece(colFrom, rowFrom)) {
                Piece pFrom = pieces[colFrom][rowFrom];
                if (validMove(pFrom, from, to))  {
                    pieces[colFrom][rowFrom] = null;

                    int colTo = to.charAt(0) - 97;
                    int rowTo = Character.getNumericValue(to.charAt(1))-1;

                    for (int i = 0; i < listenerList.size(); i ++) {
                        listenerList.get(i).onMove(from, to, pFrom);
                    }
                    if (pieces[colTo][rowTo] != null) {
                        for (int i = 0; i < listenerList.size(); i ++) {
                            listenerList.get(i).onCapture(pFrom, pieces[colTo][rowTo]);
                        }
                    }
                    pieces[colTo][rowTo] = pFrom;   
                } 
            }
        //} catch (Exception e){

       // } 
    }

    public void clear() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }
    }

    public void registerListener(BoardListener bl) {
        listenerList.add(bl);
    }

    public void removeListener(BoardListener bl) {
        if (listenerList.contains(bl)) {
            listenerList.remove(bl);
        }
    }

    public void removeAllListeners() {
        listenerList.clear();
    }

    public void iterate(BoardInternalIterator bi) {
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8;  j++) {
                String loc = getSpot(i, j);
                Piece p = pieces[i][j];
                bi.visit(loc, p);
            }
        }        
    }


    //throws an exception if there is no piece on the given location and if the location is not on the table.
    private boolean isThereChessPiece(int col, int row) {
        //check if the col and row are within the boundaries of the chess table
        if (col >= 0 && col <= 7) { // check if column is valid
            
            if (row >= 0 && row <= 7) { // check if row is valid
            
                    //check if there is a chess piece on the given location
                    if (pieces[col][row] != null) {
                        return true;
                    } else {
                        throw new ArrayIndexOutOfBoundsException();
                    }
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        } 
    }

    private boolean validMove(Piece pFrom, String from, String to) {
        if (pFrom.moves(theBoard, from).contains(to)) {
            return true;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    //get the spot for the given col and row
    private String getSpot(int col, int row) {
        return Character.toString(col + 97) + Integer.toString(row + 1);
    }
    
}
// list of listeners added in the main
// nother class when you do a move board has a list of obhect d.move()
// when you are moving
// anytime you are moving iterate theroufh listeners and 
// add a list list
// will store object listeners