public class Test {

    // Run "java -ea Test" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

    //test adding different pieces to the board
    public static void test1() {
        Board b = Board.theBoard();
        Piece.registerPiece(new QueenFactory());
        Piece.registerPiece(new KingFactory());
        Piece p1 = Piece.createPiece("bq");
        Piece p2 = Piece.createPiece("bk");
        b.addPiece(p1, "a3");
        b.addPiece(p2, "b4");
        //System.out.println(b.getPiece("a3").toString());
        //System.out.println(b.getPiece("b4").toString());
        assert b.getPiece("a3") == p1;
        assert b.getPiece("b4") == p2;
    }

    //test adding twice to the same position on the board
    public static void test2() {
        Board b = Board.theBoard();
        Piece.registerPiece(new QueenFactory());
        Piece p1 = Piece.createPiece("bq");
        Piece p2 = Piece.createPiece("wq");
        b.addPiece(p1, "a3");
        
        b.addPiece(p2, "a3");

        System.out.println(b.getPiece("a3").toString());
        assert b.getPiece("a3") == p1;
    }




    //test king moves (without edge cases!)
    public static void test3() {
        Board b = Board.theBoard();
        Piece.registerPiece(new KingFactory());
        Piece p1 = Piece.createPiece("bk");
        Piece p2 = Piece.createPiece("wk");
        b.addPiece(p1, "b3");
        System.out.println(b.getPiece("b3").toString());
        System.out.println(p1.moves(b, "b3"));

    }

    //test pawn moves (without edge cases!)
    public static void test4() {
        Board b = Board.theBoard();
        Piece.registerPiece(new PawnFactory());
        Piece p1 = Piece.createPiece("wp");
        Piece p2 = Piece.createPiece("bp");
    
        Piece p3 = Piece.createPiece("bp"); //opponents of white
        Piece p4 = Piece.createPiece("bp");

        Piece p5 = Piece.createPiece("wp"); //opponents of black
        Piece p6 = Piece.createPiece("wp");
        
        b.addPiece(p1, "c2");
        b.addPiece(p2, "c7");

        b.addPiece(p3, "b3");  //opponents of white
        b.addPiece(p4, "d3");

        b.addPiece(p5, "b6");   //opponents of black
        b.addPiece(p6, "d6");

        System.out.println(p1.moves(b, "c2"));
        System.out.println(p2.moves(b, "c7"));

    }
    
    //test knight moves (without the edge cases)
    public static void test5() {

        Board b = Board.theBoard();
        Piece.registerPiece(new KnightFactory());
        Piece p1 = Piece.createPiece("wn");
        Piece p2 = Piece.createPiece("bn");

        b.addPiece(p1, "d3");
        b.addPiece(p2, "e6");

        System.out.println(p1.moves(b, "d3"));
        System.out.println(p2.moves(b, "e6"));
    }

    //test bishop moves (without the edge cases)
    public static void test6() {

        Board b = Board.theBoard();
        Piece.registerPiece(new BishopFactory());
        Piece p1 = Piece.createPiece("wb");
        Piece p2 = Piece.createPiece("bb");

        b.addPiece(p1, "d3");
        b.addPiece(p2, "f5");

        System.out.println(b.getPiece("f5").toString());
        System.out.println(p1.moves(b, "d3"));
    }

    //test rook moves (without the edge cases)
    public static void test7() {

        Board b = Board.theBoard();
        Piece.registerPiece(new RookFactory());
        Piece p1 = Piece.createPiece("wr");
        Piece p2 = Piece.createPiece("br");

        b.addPiece(p1, "d3");
        b.addPiece(p2, "d7");

        System.out.println(p1.moves(b, "d3"));
    }

    //test queen moves (without the edge cases)
    public static void test8() {

        Board b = Board.theBoard();
        Piece.registerPiece(new QueenFactory());
        Piece p1 = Piece.createPiece("wq");
        Piece p2 = Piece.createPiece("bq");
        Piece p3 = Piece.createPiece("wq");

        b.addPiece(p1, "d3");
        b.addPiece(p2, "d8");
        b.addPiece(p3, "d7");

        System.out.println(p1.moves(b, "d7"));
    }

    


    public static void main(String[] args) {
	    //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //test6();
        //test7();
        test8();
    }

}