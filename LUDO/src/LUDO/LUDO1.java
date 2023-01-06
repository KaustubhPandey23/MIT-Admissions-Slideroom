package LUDO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import LUDO.LUDO1.Dice;
import LUDO.LUDO1.Position;
import LUDO.LUDO1.Player;
import LUDO.LUDO1.Player.Piece;

public class LUDO1 extends JPanel implements MouseListener {
	public static final int maxPositions = 72;
	public Position imagePosition;
	public Dice gameDice;

	public Player[] players = new Player[4];

	public int[] playerTurn = { 0, 1, 3, 2 };
	public int turn = 0;

	public boolean captureAvailability;
	public ArrayList<Integer> safePlaces = new ArrayList<Integer>();

	public boolean limitedSix;
	public int[] six = { 0, 0 };

	public int boardSizeX;
	public int boardSizeY;
	public int startPlaceSizeX;
	public int startPlaceSizeY;
	public int[] pieceImageSizeX = new int[6];
	public int[] pieceImageSizeY = new int[6];
	public ArrayList<Integer> reverseCoordsPos = new ArrayList<Integer>();

	public LUDO1() {
		this(true);
	}

	public LUDO1(boolean capture) {
		this(capture, true);
	}

	public LUDO1(boolean capture, boolean limsix) {
		this(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,
				Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, capture, limsix);
	}

	public LUDO1(int sizeX, int sizeY, Color a, Color b, Color c, Color d, boolean capture, boolean limsix) {
		captureAvailability = capture;
		limitedSix = limsix;

		boardSizeX = sizeX;
		boardSizeY = sizeY;
		startPlaceSizeX = boardSizeX / 3;
		startPlaceSizeY = boardSizeY / 3;
		pieceImageSizeX[0] = startPlaceSizeX / 5 - 2;
		pieceImageSizeX[1] = startPlaceSizeX / 3 - 2;
		pieceImageSizeX[2] = pieceImageSizeX[1] / 4 - 2;
		pieceImageSizeX[3] = 1;
		pieceImageSizeX[4] = startPlaceSizeX / 6 - 2;
		pieceImageSizeX[5] = pieceImageSizeX[4] / 4 - 2;

		pieceImageSizeY[0] = startPlaceSizeY / 5 - 2;
		pieceImageSizeY[1] = startPlaceSizeY / 6 - 2;
		pieceImageSizeY[2] = pieceImageSizeY[1] / 4 - 2;
		pieceImageSizeY[3] = 1;
		pieceImageSizeY[4] = startPlaceSizeY / 3 - 2;
		pieceImageSizeY[5] = pieceImageSizeY[4] / 4 - 2;

		for (int i = 5; i < 18; i++)
			reverseCoordsPos.add(i);
		for (int i = 62; i < 67; i++)
			reverseCoordsPos.add(i);
		for (int i = 31; i < 44; i++)
			reverseCoordsPos.add(i);
		for (int i = 57; i < 62; i++)
			reverseCoordsPos.add(i);

		int[] safe = { 0, 8, 13, 21, 26, 34, 39, 47 };
		for (int i = 0; i < safe.length; i++)
			safePlaces.add(safe[i]);

		players[0] = new Player(a);
		players[1] = new Player(b);
		players[2] = new Player(c);
		players[3] = new Player(d);

		int count = 0;
		while (count < 12)
			players[0].route[count] = 39 + count++;
		players[0].route[count++] = 56;
		for (int i = 0; i < 38; i++, count++)
			players[0].route[count] = i;
		for (int i = 0; count < 56; i++, count++)
			players[0].route[count] = i + 57;

		for (int i = 0; i < 56; i++)
			players[1].route[i] = i;

		for (count = 0; count < 25; count++)
			players[2].route[count] = 26 + count;
		players[2].route[count++] = 56;
		for (int i = 0; i < 25; i++, count++)
			players[2].route[count] = i;
		for (int i = 0; count < 56; i--, count++)
			players[2].route[count] = i + 71;

		for (count = 0; count < 38; count++)
			players[3].route[count] = 13 + count;
		players[3].route[count++] = 56;
		for (int i = 0; i < 12; i++, count++)
			players[3].route[count] = i;
		for (int i = 0; count < 56; i--, count++)
			players[3].route[count] = i + 66;

		imagePosition = new Position();
		gameDice = new Dice();

		setSize(sizeX, sizeY);
		addMouseListener(this);
		setFocusable(true);
		// {for(int
		// i=0;i<56;i++)System.out.print(","+players[3].route[i]);}
	}

	public BufferedImage boardImage() {
		BufferedImage boardImage = new BufferedImage(boardSizeX, boardSizeY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = boardImage.getGraphics();

		for (int i = 0; i < boardSizeX / (startPlaceSizeX / 3); i++) {
			g.setColor(Color.WHITE);
			if (i % 2 == 0)
				changeCheckerColor(g);
			for (int j = 0; j < boardSizeY / (startPlaceSizeY / 6) / 3; j++) {
				g.fillRect(startPlaceSizeX / 3 * i, startPlaceSizeY / 6 * j, startPlaceSizeX / 3, startPlaceSizeY / 6);
				changeCheckerColor(g);
			}
			for (int j = boardSizeY / (startPlaceSizeY / 6) / 3 * 2; j < boardSizeY / (startPlaceSizeY / 6); j++) {
				g.fillRect(startPlaceSizeX / 3 * i, startPlaceSizeY / 6 * j, startPlaceSizeX / 3, startPlaceSizeY / 6);
				changeCheckerColor(g);
			}
		}
		for (int i = 0; i < boardSizeX / (startPlaceSizeX / 6); i++) {
			g.setColor(Color.WHITE);
			if (i % 2 == 0)
				changeCheckerColor(g);
			for (int j = 0; j < 3; j++) {
				g.fillRect(startPlaceSizeX / 6 * i, startPlaceSizeY + startPlaceSizeY / 3 * j, startPlaceSizeX / 6,
						startPlaceSizeY / 3);
				changeCheckerColor(g);
			}
		}

		int i = 0;
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 0, startPlaceSizeY * 0, null);
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 2, startPlaceSizeY * 0, null);
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 0, startPlaceSizeY * 2, null);
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 2, startPlaceSizeY * 2, null);

		int[] coloured = { 0, 51, 52, 53, 54, 55 };
		for (i = 0; i < players.length; i++)
			for (int j = 0; j < coloured.length; j++) {
				boolean test = reverseCoordsPos.contains(players[i].route[coloured[j]]);
				g.setColor(players[i].playerColor);
				g.fillRect(imagePosition.positions[players[i].route[coloured[j]]].x,
						imagePosition.positions[players[i].route[coloured[j]]].y, startPlaceSizeX / (test ? 6 : 3),
						startPlaceSizeY / (test ? 3 : 6));
				g.setColor(Color.LIGHT_GRAY);
				g.drawRect(imagePosition.positions[players[i].route[coloured[j]]].x,
						imagePosition.positions[players[i].route[coloured[j]]].y, startPlaceSizeX / (test ? 6 : 3),
						startPlaceSizeY / (test ? 3 : 6));
			}

		BufferedImage homecumdice = new BufferedImage(startPlaceSizeX, startPlaceSizeY, BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = homecumdice.getGraphics();
		g1.setColor(Color.BLACK);
		g1.fillRect(0, 0, homecumdice.getWidth(), homecumdice.getHeight());
		g1.setColor(Color.WHITE);
		// g1.drawString("HOME", homecumdice.getWidth() / 3,
		// homecumdice.getHeight() / 3);

		g1.fillRect((homecumdice.getWidth() - gameDice.width) / 2 - 1,
				(homecumdice.getHeight() - gameDice.height) / 2 - 1, gameDice.width + 2, gameDice.height + 2);

		g.drawImage(homecumdice, homecumdice.getWidth(), homecumdice.getHeight(), null);

		return boardImage;
	}

	private void changeCheckerColor(Graphics g) {
		if (g.getColor().equals(Color.WHITE))
			g.setColor(Color.LIGHT_GRAY);
		else
			g.setColor(Color.WHITE);
	}

	public void drawPiece(Graphics g, int player, int piece) {
		if (!players[player].pieces[piece].draw)
			return;
		Position p = null;
		switch (players[player].pieces[piece].getState()) {
		case 0:
			p = imagePosition.startingPositions[player][piece];
			break;
		case 1:
			p = imagePosition.positions[players[player].pieces[piece].position];
			break;
		case 2:
			boolean test = reverseCoordsPos.contains(players[player].pieces[piece].position);

			Position[][] p2 = test ? imagePosition.overlappingPositionsReverseCoords
					: imagePosition.overlappingPositions;
			p = imagePosition.positions[players[player].pieces[piece].position]
					.add(p2[players[player].pieces[piece].overlappingState / 10
							- 2][players[player].pieces[piece].overlappingState % 10 - 1]);
			break;
		case 3:
			p = new Position(0, 0);
			break;
		}

		g.drawImage(players[player].pieces[piece].pieceImage(), p.x, p.y, null);
	}

	public void drawDice(Graphics g) {
		g.drawImage(gameDice.diceImage[gameDice.number], startPlaceSizeX + (startPlaceSizeX - gameDice.width) / 2,
				startPlaceSizeY + (startPlaceSizeY - gameDice.height) / 2, null);
	}

	public class Player {
		public Piece[] pieces = new Piece[4];

		public Color playerColor;
		public int[] route = new int[56];

		public Player(Color c) {
			playerColor = c;

			for (int i = 0; i < pieces.length; i++)
				pieces[i] = new Piece(c);
		}

		public BufferedImage startPlace() {
			BufferedImage startPlace = new BufferedImage(startPlaceSizeX, startPlaceSizeY, BufferedImage.TYPE_INT_ARGB);
			Graphics g = startPlace.getGraphics();
			g.setColor(playerColor);
			g.fillRect(0, 0, startPlaceSizeX, startPlaceSizeY);
			if (playerColor.equals(Color.WHITE))
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
			g.fillOval(startPlaceSizeX / 5 * 1, startPlaceSizeY / 5 * 1, startPlaceSizeX / 5, startPlaceSizeY / 5);
			g.fillOval(startPlaceSizeX / 5 * 3, startPlaceSizeY / 5 * 1, startPlaceSizeX / 5, startPlaceSizeY / 5);
			g.fillOval(startPlaceSizeX / 5 * 1, startPlaceSizeY / 5 * 3, startPlaceSizeX / 5, startPlaceSizeY / 5);
			g.fillOval(startPlaceSizeX / 5 * 3, startPlaceSizeY / 5 * 3, startPlaceSizeX / 5, startPlaceSizeY / 5);

			return startPlace;
		}

		public void setPlace(int piece, int place) {
			if (place < 0)
				place = -1;
			if (place >= 56)
				place = 56;
			pieces[piece].place = place;
			if (place < 0)
				pieces[piece].position = -1;
			else if (place >= 56)
				pieces[piece].position = 73;
			else
				pieces[piece].position = route[pieces[piece].place];
		}

		public void setOnStart(int piece) {
			pieces[piece].setState(Piece.STATE_INT_START);
			setPlace(piece, -1);
		}

		public void setPlaying(int piece) {
			pieces[piece].setState(Piece.STATE_INT_PLAY);
			setPlace(piece, 0);
		}

		public void setOverlapping(int piece, int overlappingstate) {
			if (overlappingstate == Piece.OVERLAP_INT_NULL)
				pieces[piece].setState(Piece.STATE_INT_PLAY, Piece.OVERLAP_INT_NULL);
			else
				pieces[piece].setState(Piece.STATE_INT_OVERLAP, overlappingstate);
		}

		public void setEnd(int piece) {
			pieces[piece].setState(Piece.STATE_INT_END);
			setPlace(piece, 56);
			turn--;
		}

		public class Piece {
			public static final int STATE_INT_START = 0, STATE_INT_PLAY = 1, STATE_INT_OVERLAP = 2, STATE_INT_END = 3;
			public static final int OVERLAP_INT_NULL = 0, OVERLAP_INT_2_1 = 21, OVERLAP_INT_2_2 = 22,
					OVERLAP_INT_3_1 = 31, OVERLAP_INT_3_2 = 32, OVERLAP_INT_3_3 = 33, OVERLAP_INT_4_1 = 41,
					OVERLAP_INT_4_2 = 42, OVERLAP_INT_4_3 = 43, OVERLAP_INT_4_4 = 44;

			private int state;
			public int overlappingState;

			public int overlaps;
			public boolean draw = true;

			public Color color;
			public int place = -1;
			public int position = -1;

			public Piece(Color c) {
				color = c;

				setState(0);
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				setState(state, 0);
			}

			public void setState(int state, int overlappingstate) {
				this.state = state;
				overlappingState = overlappingstate;
			}

			public BufferedImage pieceImage() {
				boolean test = reverseCoordsPos.contains(position);

				int p = test ? 3 : 0 + state;
				BufferedImage pieceImage = new BufferedImage(pieceImageSizeX[p], pieceImageSizeY[p],
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = pieceImage.getGraphics();
				Color color = state == 3 ? new Color(0, 0, 0, 0) : this.color;
				g.setColor(color);
				g.fillOval(0, 0, pieceImage.getWidth(), pieceImage.getHeight());
				if (state == STATE_INT_OVERLAP && overlaps > 0) {
					if (this.color.equals(Color.WHITE))
						g.setColor(Color.BLACK);
					else
						g.setColor(Color.WHITE);
					g.drawString("" + overlaps, 0, 0);
				}

				return pieceImage;
			}
		}
	}

	public class Position {
		public int x, y;

		public Position[] positions = new Position[maxPositions];
		public Position[][] overlappingPositions = new Position[3][4];
		public Position[][] overlappingPositionsReverseCoords = new Position[3][4];
		public Position[][] startingPositions = new Position[4][4];

		public Position() {
			set();
		}

		private Position(int x, int y) {
			this.x = x - 2;
			this.y = y - 2;
		}

		public Position add(Position p) {
			return new Position(x + p.x, y + p.y);
		}

		public void set() {
			int x = startPlaceSizeX / 3 + 1, y = startPlaceSizeY / 6 + 1;
			int x1 = startPlaceSizeX / 3, y1 = startPlaceSizeY / 6;
			int x2 = x + startPlaceSizeX, y2 = y + startPlaceSizeY;
			int i = 1, j = 0, k = 0;

			positions[k++] = new Position(x2 + x1 * i, y * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j++);

			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);

			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j++);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);

			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j--);

			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i--, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);

			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j--);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j--);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);

			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i++, y + y1 * j++);

			positions[k++] = new Position(x2 + x1 * i, 1);

			i = 0;
			j = 0;
			positions[k++] = new Position(x + x1 * i++, y2 + y1 * j);
			positions[k++] = new Position(x + x1 * i++, y2 + y1 * j);
			positions[k++] = new Position(x + x1 * i++, y2 + y1 * j);
			positions[k++] = new Position(x + x1 * i++, y2 + y1 * j);
			positions[k++] = new Position(x + x1 * i++, y2 + y1 * j);

			i = 1;
			positions[k++] = new Position(2 * (x2 - x) + i, y2 + y1 * j);
			positions[k++] = new Position(2 * (x2 - x) + x * i, y2 + y1 * j);
			positions[k++] = new Position(2 * (x2 - x) + x + x1 * i++, y2 + y1 * j);
			positions[k++] = new Position(2 * (x2 - x) + x + x1 * i++, y2 + y1 * j);
			positions[k++] = new Position(2 * (x2 - x) + x + x1 * i++, y2 + y1 * j);

			i = 0;
			j = 1;
			positions[k++] = new Position(x2 + x1 * i, 2 * (y2 - y) + j);
			positions[k++] = new Position(x2 + x1 * i, 2 * (y2 - y) + y * j);
			positions[k++] = new Position(x2 + x1 * i, 2 * (y2 - y) + y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, 2 * (y2 - y) + y + y1 * j++);
			positions[k++] = new Position(x2 + x1 * i, 2 * (y2 - y) + y + y1 * j++);

			overlappingPositions[0][0] = new Position(1, (pieceImageSizeY[1] - pieceImageSizeY[2]) / 2);
			overlappingPositions[0][1] = new Position(pieceImageSizeX[1] / 2 + 2,
					(pieceImageSizeY[1] - pieceImageSizeY[2]) / 2);

			overlappingPositions[1][0] = new Position((pieceImageSizeX[1] - pieceImageSizeX[2]) / 2, 1);
			overlappingPositions[1][1] = new Position(1, pieceImageSizeY[1] - pieceImageSizeY[2] - 1);
			overlappingPositions[1][2] = new Position(pieceImageSizeX[1] - pieceImageSizeX[2] - 1,
					pieceImageSizeY[1] - pieceImageSizeY[2] - 1);

			overlappingPositions[2][0] = new Position(1, 1);
			overlappingPositions[2][1] = new Position(pieceImageSizeX[1] - pieceImageSizeX[2] - 1, 1);
			overlappingPositions[2][2] = new Position(1, pieceImageSizeY[1] - pieceImageSizeY[2] - 1);
			overlappingPositions[2][3] = new Position(pieceImageSizeX[1] - pieceImageSizeX[2] - 1,
					pieceImageSizeY[1] - pieceImageSizeY[2] - 1);

			overlappingPositionsReverseCoords[0][0] = new Position(1, (pieceImageSizeY[4] - pieceImageSizeY[5]) / 2);
			overlappingPositionsReverseCoords[0][1] = new Position(pieceImageSizeX[4] / 2 + 2,
					(pieceImageSizeY[4] - pieceImageSizeY[5]) / 2);

			overlappingPositionsReverseCoords[1][0] = new Position((pieceImageSizeX[4] - pieceImageSizeX[5]) / 2, 1);
			overlappingPositionsReverseCoords[1][1] = new Position(1, pieceImageSizeY[4] - pieceImageSizeY[5] - 1);
			overlappingPositionsReverseCoords[1][2] = new Position(pieceImageSizeX[4] - pieceImageSizeX[5] - 1,
					pieceImageSizeY[4] - pieceImageSizeY[5] - 1);

			overlappingPositionsReverseCoords[2][0] = new Position(1, 1);
			overlappingPositionsReverseCoords[2][1] = new Position(pieceImageSizeX[4] - pieceImageSizeX[5] - 1, 1);
			overlappingPositionsReverseCoords[2][2] = new Position(1, pieceImageSizeY[4] - pieceImageSizeY[5] - 1);
			overlappingPositionsReverseCoords[2][3] = new Position(pieceImageSizeX[4] - pieceImageSizeX[5] - 1,
					pieceImageSizeY[4] - pieceImageSizeY[5] - 1);

			startingPositions[0][0] = new Position(startPlaceSizeX / 5 * 1 + 1, startPlaceSizeY / 5 * 1 + 1);
			startingPositions[0][1] = new Position(startPlaceSizeX / 5 * 3 + 1, startPlaceSizeY / 5 * 1 + 1);
			startingPositions[0][2] = new Position(startPlaceSizeX / 5 * 1 + 1, startPlaceSizeY / 5 * 3 + 1);
			startingPositions[0][3] = new Position(startPlaceSizeX / 5 * 3 + 1, startPlaceSizeY / 5 * 3 + 1);

			startingPositions[1][0] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 1 + 1,
					startPlaceSizeY / 5 * 1 + 1);
			startingPositions[1][1] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 3 + 1,
					startPlaceSizeY / 5 * 1 + 1);
			startingPositions[1][2] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 1 + 1,
					startPlaceSizeY / 5 * 3 + 1);
			startingPositions[1][3] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 3 + 1,
					startPlaceSizeY / 5 * 3 + 1);

			startingPositions[2][0] = new Position(startPlaceSizeX / 5 * 1 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 1 + 1);
			startingPositions[2][1] = new Position(startPlaceSizeX / 5 * 3 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 1 + 1);
			startingPositions[2][2] = new Position(startPlaceSizeX / 5 * 1 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 3 + 1);
			startingPositions[2][3] = new Position(startPlaceSizeX / 5 * 3 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 3 + 1);

			startingPositions[3][0] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 1 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 1 + 1);
			startingPositions[3][1] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 3 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 1 + 1);
			startingPositions[3][2] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 1 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 3 + 1);
			startingPositions[3][3] = new Position(boardSizeX - startPlaceSizeX + startPlaceSizeX / 5 * 3 + 1,
					boardSizeY - startPlaceSizeY + startPlaceSizeY / 5 * 3 + 1);
		}
	}

	public class Dice {
		public BufferedImage diceImage[] = new BufferedImage[6], dot;
		public int number;
		public boolean rolled;

		public int width, height, dotWidth, dotHeight;

		public Dice() {
			width = startPlaceSizeX / 3 - 2;
			height = startPlaceSizeY / 3 - 2;
			dotWidth = width / 6;
			dotHeight = height / 6;
			dot = new BufferedImage(dotWidth, dotHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics g, g1 = dot.getGraphics();
			g1.setColor(Color.BLUE);
			g1.fillOval(0, 0, dot.getWidth(), dot.getHeight());
			for (int i = 0; i < diceImage.length; i++) {
				diceImage[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				g = diceImage[i].getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, diceImage[i].getWidth(), diceImage[i].getHeight());
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, diceImage[i].getWidth(), diceImage[i].getHeight());
			}
			g = diceImage[0].getGraphics();
			g.drawImage(dot, (width - dotWidth) / 2, (height - dotHeight) / 2, null);

			g = diceImage[1].getGraphics();
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight) / 2, null);

			g = diceImage[2].getGraphics();
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth) / 2, (height - dotHeight) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight + height / 2) / 2, null);

			g = diceImage[3].getGraphics();
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight + height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight + height / 2) / 2, null);

			g = diceImage[4].getGraphics();
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth) / 2, (height - dotHeight) / 2, null);
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight + height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight + height / 2) / 2, null);

			g = diceImage[5].getGraphics();
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight - height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight) / 2, null);
			g.drawImage(dot, (width - dotWidth - width / 2) / 2, (height - dotHeight + height / 2) / 2, null);
			g.drawImage(dot, (width - dotWidth + width / 2) / 2, (height - dotHeight + height / 2) / 2, null);

			roll();
		}

		public void roll() {
			number = (int) (Math.random() * 6);
		}
	}

	public void mouseEntered(MouseEvent e) {
		e.consume();
	}

	public void mouseExited(MouseEvent e) {
		e.consume();
	}

	public void mouseClicked(MouseEvent e) {
		e.consume();
	}

	public void mouseReleased(MouseEvent e) {
		e.consume();
	}

	public void mousePressed(MouseEvent e) {
		int x1 = startPlaceSizeX;
		int x2 = 2 * startPlaceSizeX;
		int y1 = startPlaceSizeY;
		int y2 = 2 * startPlaceSizeY;

		if (e.getX() >= x1 && e.getX() < x2 && e.getY() >= y1 && e.getY() < y2)
			rollDice();
		else {
			int i = playerTurn[turn % players.length];
			if (gameDice.rolled)
				for (int j = 0; j < players[i].pieces.length; j++)
					if (contains(i, j, e.getX(), e.getY())) {
						update(i, j);
						break;
					}
		}

		e.consume();
	}

	public boolean contains(int player, int piece, int x, int y) {
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		if (players[player].pieces[piece].getState() == 0) {
			x2 = startPlaceSizeX;
			y2 = startPlaceSizeY;
			switch (player) {
			case 1:
				x1 = 2 * x2;
				x2 *= 3;
				break;
			case 2:
				y1 = 2 * y2;
				y2 *= 3;
				break;
			case 3:
				x1 = 2 * x2;
				y1 = 2 * y2;
				x2 *= 3;
				y2 *= 3;
				break;
			}
		} else if (players[player].pieces[piece].getState() == 1 || players[player].pieces[piece].getState() == 2) {
			x1 = imagePosition.positions[players[player].pieces[piece].position].x - 1;
			y1 = imagePosition.positions[players[player].pieces[piece].position].y - 1;

			boolean test = reverseCoordsPos.contains(players[player].pieces[piece].position);

			x2 = x1 + startPlaceSizeX / (test ? 6 : 3);
			y2 = y1 + startPlaceSizeY / (test ? 3 : 6);
		} else
			return false;

		if (x >= x1 && x < x2 && y >= y1 && y < y2)
			return true;
		return false;
	}

	public synchronized void rollDice() {
		if (gameDice.rolled)
			return;
		gameDice.rolled = true;
		gameDice.roll();

		int playable = 0, restable = 0, playing = 0, resting = 0;
		int i = playerTurn[turn % players.length], k = -1, l = -1, m = -1, n = -1;
		for (int j = 0; j < players[i].pieces.length; j++) {
			if (players[i].pieces[j].getState() == Piece.STATE_INT_PLAY
					|| players[i].pieces[j].getState() == Piece.STATE_INT_OVERLAP) {
				playing++;
				if (playing == 1)
					k = j;
				if (gameDice.number + 1 + players[i].pieces[j].place >= 0
						&& gameDice.number + 1 + players[i].pieces[j].place <= 56) {
					playable++;
					if (playable == 1)
						l = j;
				}
			}
			if (players[i].pieces[j].getState() == Piece.STATE_INT_START) {
				resting++;
				if (resting == 1)
					m = j;
				if (gameDice.number == 5) {
					restable++;
					if (restable == 1)
						n = j;
				}
			}
		}

		if (six[1] == playerTurn[turn % players.length] && six[0] == 2 && gameDice.number == 5) {
			if (playing == 1)
				if (k >= 0)
					update(i, k);
		} else {
			if (playable == 1 && restable == 0)
				if (l >= 0)
					update(i, l);
			if (playable == 0 && restable > 0)
				if (n >= 0)
					update(i, n);
			if (playable == 0 && restable == 0)
				update();
		}
	}

	public synchronized boolean movePiece(int player, int piece, int blocks) {
		if (!gameDice.rolled)
			return false;
		if (!(players[player].pieces[piece].getState() == Piece.STATE_INT_PLAY
				|| players[player].pieces[piece].getState() == Piece.STATE_INT_OVERLAP))
			return false;
		if (blocks + players[player].pieces[piece].place == 56) {
			players[player].setEnd(piece);
			return false;
		}
		if (!(blocks + players[player].pieces[piece].place >= 0 && blocks + players[player].pieces[piece].place < 56))
			return false;
		players[player].setPlace(piece, players[player].pieces[piece].place + blocks);

		boolean capture = false;
		;
		if (captureAvailability)
			capture = (!safePlaces.contains(players[player].pieces[piece].position));

		ArrayList<Integer> oP = new ArrayList<Integer>(), op = new ArrayList<Integer>();

		if (capture) {
			for (int i = 0; i < players.length; i++)
				for (int j = 0; j < players[i].pieces.length; j++)
					if (players[player].pieces[piece].position == players[i].pieces[j].position)
						if (player == i) {
							oP.add(i);
							op.add(j);
						} else
							players[i].setOnStart(j);
		} else
			for (int i = 0; i < players.length; i++)
				for (int j = 0; j < players[player].pieces.length; j++)
					if (players[player].pieces[piece].position == players[i].pieces[j].position) {
						oP.add(i);
						op.add(j);
					}
		int[] OP = new int[oP.size()], Op = new int[op.size()];
		for (int i = 0; i < oP.size(); i++) {
			OP[i] = oP.get(i).intValue();
			Op[i] = op.get(i).intValue();
		}
		overlap(OP, Op);

		return true;
	}

	public synchronized void overlap(int[] player, int[] piece) {
		if (player.length == 0)
			return;
		if (player.length == 1) {
			players[player[0]].setOverlapping(piece[0], Piece.OVERLAP_INT_NULL);
			return;
		}
		int[] count = { 0, 0, 0, 0 };
		for (int i = 0; i < player.length; i++) {
			count[player[i]]++;
			if (count[player[i]] > 1)
				players[player[i]].pieces[piece[i]].draw = false;
		}

		for (int i = 0; i < player.length; i++)
			players[player[i]].pieces[piece[i]].overlaps = count[player[i]];

		int over = 0;
		for (int i = 0; i < count.length; i++)
			if (count[i] > 0)
				over++;

		int check = player[0], mod = 1;
		for (int i = 0; i < player.length; i++) {
			players[player[i]].setOverlapping(piece[i], over * 10 + (player[i] == check ? mod : ++mod));
			check = player[i];
		}
	}

	public synchronized void six() {
		if (!limitedSix) {
			turn--;
			return;
		}
		if (playerTurn[turn % players.length] != six[1]) {
			six[0] = 0;
			six[1] = playerTurn[turn % players.length];
		}
		turn--;
		six[0]++;
	}

	public synchronized void update(int player, int piece) {
		if (!gameDice.rolled)
			return;
		boolean moved = movePiece(player, piece, gameDice.number + 1);
		if (gameDice.number == 5) {
			six();
			if (six[0] >= 3 && !(players[player].pieces[piece].getState() == Piece.STATE_INT_START)) {
				players[player].setOnStart(piece);
				turn++;
				update();
			} else {
				if (players[player].pieces[piece].getState() == Piece.STATE_INT_START)
					players[player].setPlaying(piece);
				update();
				rollDice();
			}
		} else if (moved)
			update();

	}

	public synchronized void update() {
		gameDice.rolled = false;
		turn++;
	}

	public void draw(Graphics g) {
		g.drawImage(boardImage(), 0, 0, null);
		drawDice(g);
		for (int i = 0; i < players.length; i++)
			for (int j = 0; j < players[i].pieces.length; j++)
				drawPiece(g, i, j);
	}

	public void paintComponent(Graphics g) {
		draw(g);
		try {
			Thread.sleep(20);
		} catch (Exception ex) {
		}
		repaint();
	}
}

// class Main extends JFrame {
// static JFrame frame = new Main();
//
// Main() {
// setFont(new Font("Arial", Font.PLAIN, 48));
// add(new LUDO());
// setUndecorated(true);
// setSize(Toolkit.getDefaultToolkit().getScreenSize());
// setVisible(true);
// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// setFont(new Font("Arial", Font.PLAIN,
// (Toolkit.getDefaultToolkit().getScreenSize().height / 3 / 6 - 2) / 4 - 2));
// }
//
// public static void main(String[] args) {
// }
// }
