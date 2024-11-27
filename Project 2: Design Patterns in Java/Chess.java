import java.io.*;
import java.nio.*;

public class Chess {

	public static void run(Board b, String location, String piece) {
		//System.out.println(location);
		//System.out.println(piece);
		Piece p = Piece.createPiece(piece);
		b.addPiece(p, location);
	}

    public static void main(String[] args) {
	if (args.length != 2) {
	    System.out.println("Usage: java Chess layout moves");
	}
	
	Piece.registerPiece(new KingFactory());
	Piece.registerPiece(new QueenFactory());
	Piece.registerPiece(new KnightFactory());
	Piece.registerPiece(new BishopFactory());
	Piece.registerPiece(new RookFactory());
	Piece.registerPiece(new PawnFactory());
	Board.theBoard().registerListener(new Logger());
	// args[0] is the layout file name
	// args[1] is the moves file name
	// Put your code to read the layout file and moves files
	// here.
	
	BufferedReader br = null;
	File f = null; 
	Board b = Board.theBoard(); //create board

	try {
		String currLine;
		f = new File(args[0]);
		br = new BufferedReader(new FileReader(f));

		while((currLine = br.readLine()) != null) {
			if (currLine.charAt(0) != '#' && !currLine.isEmpty()) {
				String location = currLine.substring(0, 2);
				String piece = currLine.substring(3, 5);
				run(b, location, piece);
				// System.out.println(location);
				// System.out.println(piece);
        		// Piece p = Piece.createPiece(piece);
        		// b.addPiece(p, location);
			}
			
		}

	} catch (Exception e) {
		e.printStackTrace();

	} finally {
		try {
			if (br != null) {
				br.close();
			} 
		} catch (Exception ex) {
				ex.printStackTrace();
		}
	}

	try {
		String currLine;
		f = new File(args[1]);
		br = new BufferedReader(new FileReader(f));

		while((currLine = br.readLine()) != null) {
			if (currLine.charAt(0) != '#' && !currLine.isEmpty()) {
				String from = currLine.substring(0, 2);
				String to = currLine.substring(3, 5);
				b.movePiece(from, to);
			}
			
		}

	} catch (Exception e) {
		e.printStackTrace();

	} finally {
		try {
			if (br != null) {
				br.close();
			} 
		} catch (Exception ex) {
				ex.printStackTrace();
		}
	}





	// Leave the following code at the end of the simulation:
		System.out.println("Final board:");
		Board.theBoard().iterate(new BoardPrinter());
	}
}