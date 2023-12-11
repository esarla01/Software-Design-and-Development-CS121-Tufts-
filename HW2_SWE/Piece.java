import java.util.*;

abstract public class Piece {
    static HashMap <Character, PieceFactory> pieceMap = new HashMap <Character, PieceFactory> ();
    protected Color pieceColor;
    public static void registerPiece(PieceFactory pf) {
        pieceMap.put(pf.symbol(), pf);
    }

    public static Piece createPiece(String name) {

        if (name.charAt(0) == 'w') {
            Piece p = pieceMap.get(name.charAt(1)).create(Color.WHITE);
            return p;
        }
        else if (name.charAt(0) == 'b') {
            Piece p = pieceMap.get(name.charAt(1)).create(Color.BLACK);
            return p;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        
    }

    public Color color() {
        return pieceColor;
    }



    // You should write code here and just inherit it in
	// subclasses. For this to work, you should know
	// that subclasses can access superclass fields.

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}