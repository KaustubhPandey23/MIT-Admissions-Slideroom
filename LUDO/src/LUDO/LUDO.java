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

import LUDO.LUDO.Dice;
import LUDO.LUDO.Position;
import LUDO.LUDO.Player;
import LUDO.LUDO.Player.Piece;

public class LUDO extends JPanel implements MouseListener {
	public static final int maxPlaces = 56;
	public static final int maxPositions = 72;
	public Position imagePosition;
	public Dice gameDice;

	public Player[] players = new Player[4];

	public int[] playerTurn = { 0, 1, 3, 2 };
	public int turn = 0;
	public BufferedImage turnImage;

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

	public LUDO() {
		this(true);
	}

	public LUDO(boolean capture) {
		this(capture, true);
	}

	public LUDO(boolean capture, boolean limsix) {
		this(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height,
				Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, capture, limsix);
	}

	public LUDO(int sizeX, int sizeY, Color a, Color b, Color c, Color d, boolean capture, boolean limsix) {
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

		turnImage = new BufferedImage(startPlaceSizeX / 5, startPlaceSizeY / 5 * 3, BufferedImage.TYPE_INT_ARGB);
		Graphics g = turnImage.getGraphics();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, (int) (turnImage.getHeight() * 1.5)));
		g.drawString("!", 0, turnImage.getHeight());

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

		ArrayList<Integer> colored = new ArrayList<Integer>();
		int[] coloured = { 0, 51, 52, 53, 54, 55 };
		for (int j = 0; j < coloured.length; j++)
			for (int i = 0; i < players.length; i++)
				colored.add(players[i].route[coloured[j]]);

		for (int i = 0; i < maxPositions; i++) {
			boolean test = reverseCoordsPos.contains(i);
			boolean test2 = colored.contains(i);
			Color color = test2 ? players[colored.indexOf(i) % players.length].playerColor : Color.WHITE;
			g.setColor(color);
			g.fillRect(imagePosition.positions[i].x - 1, imagePosition.positions[i].y - 1,
					startPlaceSizeX / (test ? 6 : 3), startPlaceSizeY / (test ? 3 : 6));
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(imagePosition.positions[i].x - 1, imagePosition.positions[i].y - 1,
					startPlaceSizeX / (test ? 6 : 3), startPlaceSizeY / (test ? 3 : 6));
		}

		int i = 0;
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 0, startPlaceSizeY * 0, null);
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 2, startPlaceSizeY * 0, null);
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 0, startPlaceSizeY * 2, null);
		g.drawImage(players[i++].startPlace(), startPlaceSizeX * 2, startPlaceSizeY * 2, null);

		BufferedImage homecumdice = new BufferedImage(startPlaceSizeX, startPlaceSizeY, BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = homecumdice.getGraphics();
		g1.setColor(Color.BLACK);
		g1.fillRect(0, 0, homecumdice.getWidth(), homecumdice.getHeight());
		g1.setColor(Color.WHITE);
		// g1.drawString("HOME", homecumdice.getWidth() / 3,
		// homecumdice.getHeight() / 3);

		g1.fillRect(
				(homecumdice.getWidth() - gameDice.width) / 2 - 1, (homecumdice.getHeight()
						- (homecumdice.getHeight() - gameDice.height - gameDice.height / 2) / 3 - gameDice.height) - 1,
				gameDice.width + 2, gameDice.height + 2);

		g1.fillRect((homecumdice.getWidth() - gameDice.width / 2 * 3) / 3 - 1,
				(homecumdice.getHeight() - gameDice.height - gameDice.height / 2) / 3 - 1, gameDice.width / 2 + 2,
				gameDice.height / 2 + 2);
		g1.fillRect((homecumdice.getWidth() - gameDice.width / 2) / 2 - 1,
				(homecumdice.getHeight() - gameDice.height - gameDice.height / 2) / 3 - 1, gameDice.width / 2 + 2,
				gameDice.height / 2 + 2);
		g1.fillRect(
				(homecumdice.getWidth() - (homecumdice.getWidth() - gameDice.width / 2 * 3) / 3 - gameDice.width / 2)
						- 1,
				(homecumdice.getHeight() - gameDice.height - gameDice.height / 2) / 3 - 1, gameDice.width / 2 + 2,
				gameDice.height / 2 + 2);

		g.drawImage(homecumdice, homecumdice.getWidth(), homecumdice.getHeight(), null);

		return boardImage;
	}

	public void drawPiece(Graphics g, int player, int piece) {
		if (players[player].pieces[piece].getState() == Piece.STATE_INT_OVERLAP
				&& (!players[player].pieces[piece].overlapDraw))
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
							- 1][players[player].pieces[piece].overlappingState % 10 - 1]);
			break;
		case 3:
			p = new Position(0, 0);
			break;
		}

		g.drawImage(players[player].pieces[piece].pieceImage(), p.x, p.y, null);
	}

	public void drawDice(Graphics g) {
		if (gameDice.memory != null) {
			if (gameDice.memory.size() > 0)
				g.drawImage(gameDice.diceImageMemory[gameDice.memory.get(0).intValue()],
						startPlaceSizeX + (startPlaceSizeX - gameDice.width / 2 * 3) / 3,
						startPlaceSizeY + (startPlaceSizeY - gameDice.height - gameDice.height / 2) / 3, null);
			if (gameDice.memory.size() > 1)
				g.drawImage(gameDice.diceImageMemory[gameDice.memory.get(1).intValue()],
						startPlaceSizeX + (startPlaceSizeX - gameDice.width / 2) / 2,
						startPlaceSizeY + (startPlaceSizeY - gameDice.height - gameDice.height / 2) / 3, null);
			if (gameDice.memory.size() > 2)
				g.drawImage(gameDice.diceImageMemory[gameDice.memory.get(2).intValue()],
						startPlaceSizeX * 2 - (startPlaceSizeX - gameDice.width / 2 * 3) / 3 - gameDice.width / 2,
						startPlaceSizeY + (startPlaceSizeY - gameDice.height - gameDice.height / 2) / 3, null);
		}

		g.drawImage(gameDice.diceImage[gameDice.number], startPlaceSizeX + (startPlaceSizeX - gameDice.width) / 2,
				startPlaceSizeY * 2 - (startPlaceSizeY - gameDice.height - gameDice.height / 2) / 3 - gameDice.height,
				null);
	}

	public void setOnStart(int player, int piece) {
		if (players[player].pieces[piece].getState() == Piece.STATE_INT_OVERLAP)
			overlapPiece(player, piece, true);

		players[player].pieces[piece].setState(Piece.STATE_INT_START);
		players[player].setPlace(piece, -1);
	}

	public void setPlaying(int player, int piece) {
		players[player].pieces[piece].setState(Piece.STATE_INT_PLAY);
		players[player].setPlace(piece, 0);

		overlapPiece(player, piece);
	}

	public void setOverlapping(int player, int piece, int overlappingstate) {
		if (overlappingstate == Piece.OVERLAP_INT_NULL) {
			players[player].pieces[piece].setState(Piece.STATE_INT_PLAY, Piece.OVERLAP_INT_NULL);
		} else
			players[player].pieces[piece].setState(Piece.STATE_INT_OVERLAP, overlappingstate);
	}

	public void setEnd(int player, int piece) {
		if (players[player].pieces[piece].getState() == Piece.STATE_INT_OVERLAP)
			overlapPiece(player, piece, true);

		players[player].pieces[piece].setState(Piece.STATE_INT_END);
		players[player].setPlace(piece, 56);
		turn--;
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

		public class Piece {
			public static final int STATE_INT_START = 0, STATE_INT_PLAY = 1, STATE_INT_OVERLAP = 2, STATE_INT_END = 3;
			public static final int OVERLAP_INT_NULL = 0, OVERLAP_INT_1_1 = 11, OVERLAP_INT_2_1 = 21,
					OVERLAP_INT_2_2 = 22, OVERLAP_INT_3_1 = 31, OVERLAP_INT_3_2 = 32, OVERLAP_INT_3_3 = 33,
					OVERLAP_INT_4_1 = 41, OVERLAP_INT_4_2 = 42, OVERLAP_INT_4_3 = 43, OVERLAP_INT_4_4 = 44;

			private int state;
			public int overlappingState;

			public int overlaps;
			public boolean overlapDraw = true;

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
				boolean check = overlappingState == Piece.OVERLAP_INT_1_1;

				int p = state + (check ? (test ? 2 : -1) : (test ? 3 : 0));
				BufferedImage pieceImage = new BufferedImage(pieceImageSizeX[p], pieceImageSizeY[p],
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = pieceImage.getGraphics();
				Color color = state == 3 ? new Color(0, 0, 0, 0) : this.color;
				g.setColor(color);
				g.fillOval(0, 0, pieceImage.getWidth(), pieceImage.getHeight());
				if (this.color.equals(Color.WHITE))
					g.setColor(Color.BLACK);
				else
					g.setColor(Color.WHITE);
				g.drawOval(0, 0, pieceImage.getWidth(), pieceImage.getHeight());
				if (getState() == STATE_INT_OVERLAP && overlaps > 1) {
					if (this.color.equals(Color.WHITE))
						g.setColor(Color.BLACK);
					else
						g.setColor(Color.WHITE);
					int x = 0, y = pieceImage.getHeight();
					if (!test && check)
						x = (pieceImage.getWidth() - pieceImageSizeX[2]) / 2;
					g.setFont(new Font("Arial", Font.PLAIN, (int) (pieceImage.getHeight())));
					g.drawString("" + overlaps, x, y);
				}

				return pieceImage;
			}
		}
	}

	public class Position {
		public int x, y;

		public Position[] positions = new Position[maxPositions];
		public Position[][] overlappingPositions = new Position[4][4];
		public Position[][] overlappingPositionsReverseCoords = new Position[4][4];
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
			int x = startPlaceSizeX / 3, y = startPlaceSizeY / 6;
			int x1 = startPlaceSizeX / 6, y1 = startPlaceSizeY / 3;
			int x2 = startPlaceSizeX, y2 = startPlaceSizeY;
			int i = 0, j = 1, k = 0;

			positions[k++] = new Position(1 + x2 + x * 2, 1 + y * j++);
			positions[k++] = new Position(1 + x2 + x * 2, 1 + y * j++);
			positions[k++] = new Position(1 + x2 + x * 2, 1 + y * j++);
			positions[k++] = new Position(1 + x2 + x * 2, 1 + y * j++);
			positions[k++] = new Position(1 + x2 + x * 2, 1 + y * j++);

			j = 0;
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i, 1 + y2 + y1 * j++);

			positions[k++] = new Position(1 + x2 + x * 3 + x1 * i, 1 + y2 + y1 * j++);

			positions[k++] = new Position(1 + x2 * 2 + x1 * i--, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i--, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i--, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i--, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i--, 1 + y2 + y1 * j);
			positions[k++] = new Position(1 + x2 * 2, 1 + y2 + y1 * j);

			i = 1;
			j = 0;
			positions[k++] = new Position(1 + x2 * 2 - x * i, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 * 2 - x * i, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 * 2 - x * i, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 * 2 - x * i, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 * 2 - x * i, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 * 2 - x * i++, 1 + y2 * 2 + y * j);

			positions[k++] = new Position(1 + x2 * 2 - x * i++, 1 + y2 * 2 + y * j);

			positions[k++] = new Position(1 + x2, 1 + y2 * 2 + y * j--);
			positions[k++] = new Position(1 + x2, 1 + y2 * 2 + y * j--);
			positions[k++] = new Position(1 + x2, 1 + y2 * 2 + y * j--);
			positions[k++] = new Position(1 + x2, 1 + y2 * 2 + y * j--);
			positions[k++] = new Position(1 + x2, 1 + y2 * 2 + y * j--);
			positions[k++] = new Position(1 + x2, 1 + y2 * 2);

			i = 1;
			j = 1;
			positions[k++] = new Position(1 + x2 - x1 * i++, 1 + y2 * 2 - y1 * j);
			positions[k++] = new Position(1 + x2 - x1 * i++, 1 + y2 * 2 - y1 * j);
			positions[k++] = new Position(1 + x2 - x1 * i++, 1 + y2 * 2 - y1 * j);
			positions[k++] = new Position(1 + x2 - x1 * i++, 1 + y2 * 2 - y1 * j);
			positions[k++] = new Position(1 + x2 - x1 * i++, 1 + y2 * 2 - y1 * j);
			positions[k++] = new Position(1 + x2 - x1 * i, 1 + y2 * 2 - y1 * j++);

			positions[k++] = new Position(1 + x2 - x1 * i, 1 + y2 * 2 - y1 * j++);

			positions[k++] = new Position(1 + x2 - x1 * i--, 1 + y2);
			positions[k++] = new Position(1 + x2 - x1 * i--, 1 + y2);
			positions[k++] = new Position(1 + x2 - x1 * i--, 1 + y2);
			positions[k++] = new Position(1 + x2 - x1 * i--, 1 + y2);
			positions[k++] = new Position(1 + x2 - x1 * i--, 1 + y2);
			positions[k++] = new Position(1 + x2 - x1 * i, 1 + y2);

			i = 0;
			j = 1;
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j++);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j++);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j++);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j++);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j++);
			positions[k++] = new Position(1 + x2 + x * i++, 1 + y2 - y * j);

			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j--);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j--);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j--);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j--);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j--);
			positions[k++] = new Position(1 + x2 + x * i, 1 + y2 - y * j);

			positions[k++] = new Position(1 + x2 + x * 2, 1);

			i = 1;
			positions[k++] = new Position(1 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x1 * i, 1 + y2 + y1);

			i = 0;
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i++, 1 + y2 + y1);
			positions[k++] = new Position(1 + x2 * 2 + x1 * i, 1 + y2 + y1);

			j = 0;
			positions[k++] = new Position(1 + x2 + x, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 + x, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 + x, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 + x, 1 + y2 * 2 + y * j++);
			positions[k++] = new Position(1 + x2 + x, 1 + y2 * 2 + y * j);

			overlappingPositions[0][0] = new Position(0, 0);

			overlappingPositions[1][0] = new Position(1, (pieceImageSizeY[1] - pieceImageSizeY[2]) / 2);
			overlappingPositions[1][1] = new Position(pieceImageSizeX[1] / 2 + 2,
					(pieceImageSizeY[1] - pieceImageSizeY[2]) / 2);

			overlappingPositions[2][0] = new Position((pieceImageSizeX[1] - pieceImageSizeX[2]) / 2, 1);
			overlappingPositions[2][1] = new Position(1, pieceImageSizeY[1] - pieceImageSizeY[2] - 1);
			overlappingPositions[2][2] = new Position(pieceImageSizeX[1] - pieceImageSizeX[2] - 1,
					pieceImageSizeY[1] - pieceImageSizeY[2] - 1);

			overlappingPositions[3][0] = new Position(1, 1);
			overlappingPositions[3][1] = new Position(pieceImageSizeX[1] - pieceImageSizeX[2] - 1, 1);
			overlappingPositions[3][2] = new Position(1, pieceImageSizeY[1] - pieceImageSizeY[2] - 1);
			overlappingPositions[3][3] = new Position(pieceImageSizeX[1] - pieceImageSizeX[2] - 1,
					pieceImageSizeY[1] - pieceImageSizeY[2] - 1);

			overlappingPositionsReverseCoords[0][0] = new Position(0, 0);

			overlappingPositionsReverseCoords[1][0] = new Position(1, (pieceImageSizeY[4] - pieceImageSizeY[5]) / 2);
			overlappingPositionsReverseCoords[1][1] = new Position(pieceImageSizeX[4] / 2 + 2,
					(pieceImageSizeY[4] - pieceImageSizeY[5]) / 2);

			overlappingPositionsReverseCoords[2][0] = new Position((pieceImageSizeX[4] - pieceImageSizeX[5]) / 2, 1);
			overlappingPositionsReverseCoords[2][1] = new Position(1, pieceImageSizeY[4] - pieceImageSizeY[5] - 1);
			overlappingPositionsReverseCoords[2][2] = new Position(pieceImageSizeX[4] - pieceImageSizeX[5] - 1,
					pieceImageSizeY[4] - pieceImageSizeY[5] - 1);

			overlappingPositionsReverseCoords[3][0] = new Position(1, 1);
			overlappingPositionsReverseCoords[3][1] = new Position(pieceImageSizeX[4] - pieceImageSizeX[5] - 1, 1);
			overlappingPositionsReverseCoords[3][2] = new Position(1, pieceImageSizeY[4] - pieceImageSizeY[5] - 1);
			overlappingPositionsReverseCoords[3][3] = new Position(pieceImageSizeX[4] - pieceImageSizeX[5] - 1,
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
		public BufferedImage diceImage[] = new BufferedImage[6], diceImageMemory[] = new BufferedImage[6], dot;
		public int number;
		public ArrayList<Integer> memory;
		public boolean rolled;

		public int width, height, dotWidth, dotHeight;

		public Dice() {
			width = startPlaceSizeX / 3 - 2;
			height = startPlaceSizeY / 3 - 2;
			dotWidth = width / 6;
			dotHeight = height / 6;
			dot = new BufferedImage(dotWidth, dotHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics g = dot.getGraphics();
			g.setColor(Color.BLUE);
			g.fillOval(0, 0, dot.getWidth(), dot.getHeight());
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

			int width2 = width / 2, height2 = height / 2, dotWidth2 = dotWidth / 2, dotHeight2 = dotHeight / 2;
			BufferedImage dot = new BufferedImage(dotWidth2, dotHeight2, BufferedImage.TYPE_INT_ARGB);
			g = dot.getGraphics();
			g.setColor(Color.BLUE);
			g.fillOval(0, 0, dot.getWidth(), dot.getHeight());
			for (int i = 0; i < diceImageMemory.length; i++) {
				diceImageMemory[i] = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_ARGB);
				g = diceImageMemory[i].getGraphics();
				g.setColor(Color.RED);
				g.fillRect(0, 0, diceImageMemory[i].getWidth(), diceImageMemory[i].getHeight());
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, diceImageMemory[i].getWidth(), diceImageMemory[i].getHeight());
			}
			g = diceImageMemory[0].getGraphics();
			g.drawImage(dot, (width2 - dotWidth2) / 2, (height2 - dotHeight2) / 2, null);

			g = diceImageMemory[1].getGraphics();
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2) / 2, null);

			g = diceImageMemory[2].getGraphics();
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2) / 2, (height2 - dotHeight2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);

			g = diceImageMemory[3].getGraphics();
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);

			g = diceImageMemory[4].getGraphics();
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2) / 2, (height2 - dotHeight2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);

			g = diceImageMemory[5].getGraphics();
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 - height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 - width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);
			g.drawImage(dot, (width2 - dotWidth2 + width2 / 2) / 2, (height2 - dotHeight2 + height2 / 2) / 2, null);

			number = (int) (Math.random() * 6);
		}

		public void roll() {
			number = (int) (Math.random() * 6);
			// rollAsInstructed();
			memory.add(number);
		}

		public int[] instruction = {1, 6,6,1, 3, 4, 1, 6,5, 3, 4, 1, 6,6,6};
		int instruct;

		public void rollAsInstructed() {
			number = instruction[instruct % instruction.length] - 1;
			instruct++;
		}

		public void reset() {
			memory = new ArrayList<Integer>();
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

		if (e.getX() >= x1 && e.getX() < x2 && e.getY() >= y1 && e.getY() < y2) {
			if (!gameDice.rolled)
				gameDice.reset();
			rollDice();
		} else {
			int i = playerTurn[turn % players.length];
			if (gameDice.rolled)
				for (int j = 0; j < players[i].pieces.length; j++)
					if (contains(i, j, e.getX(), e.getY())) {
						update(i, j);
						e.consume();
						return;
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

		ArrayList<Integer> playable = new ArrayList<Integer>(), restable = new ArrayList<Integer>(),
				playing = new ArrayList<Integer>(), resting = new ArrayList<Integer>();
		int i = playerTurn[turn % players.length];
		for (int j = 0; j < players[i].pieces.length; j++) {
			if (players[i].pieces[j].getState() == Piece.STATE_INT_PLAY
					|| players[i].pieces[j].getState() == Piece.STATE_INT_OVERLAP) {
				playing.add(j);
				if (gameDice.number + 1 + players[i].pieces[j].place >= 0
						&& gameDice.number + 1 + players[i].pieces[j].place <= 56) {
					boolean haveAlready = false;
					for (int k = 0; k < playable.size(); k++)
						if (players[i].pieces[j].position == players[i].pieces[playable.get(k)].position)
							haveAlready = true;
					if (!haveAlready)
						playable.add(j);
				}
			}
			if (players[i].pieces[j].getState() == Piece.STATE_INT_START) {
				resting.add(j);
				if (gameDice.number == 5)
					restable.add(j);

			}
		}

		if (six[1] == playerTurn[turn % players.length] && six[0] == 2 && gameDice.number == 5) {
			if (playing.size() == 1)
				update(i, playing.get(0).intValue());
		} else {
			if (playable.size() == 1 && restable.size() == 0)
				update(i, playable.get(0).intValue());
			if (playable.size() == 0 && restable.size() > 0)
				update(i, restable.get(0).intValue());
			if (playable.size() == 0 && restable.size() == 0)
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
			setEnd(player, piece);
			if (blocks == 6)
				turn++;
			return true;
		}

		if (!(blocks + players[player].pieces[piece].place >= 0 && blocks + players[player].pieces[piece].place < 56))
			return false;

		overlapPiece(player, piece, true);

		players[player].setPlace(piece, players[player].pieces[piece].place + blocks);

		boolean capture = false;
		if (captureAvailability)
			capture = (!safePlaces.contains(players[player].pieces[piece].position));

		overlapPieceCapture(player, piece, capture);

		return true;
	}

	public void overlapPiece(int player, int piece) {
		overlapPiece(player, piece, false);
	}

	public void overlapPiece(int player, int piece, boolean exclude) {
		overlapPiece(player, piece, exclude, false);
	}

	public void overlapPieceCapture(int player, int piece, boolean capture) {
		overlapPiece(player, piece, false, capture);
	}

	public void overlapPiece(int player, int piece, boolean exclude, boolean capture) {
		ArrayList<Integer> oP = new ArrayList<Integer>(), op = new ArrayList<Integer>();
		for (int i = 0; i < players.length; i++)
			for (int j = 0; j < players[player].pieces.length; j++)
				if (players[player].pieces[piece].position == players[i].pieces[j].position) {
					if (capture) {
						if (player == i) {
							oP.add(i);
							op.add(j);
						} else
							setOnStart(i, j);
					} else if (exclude) {
						if (!(player == i && piece == j)) {
							oP.add(i);
							op.add(j);
						}
					} else {
						oP.add(i);
						op.add(j);
					}
					players[i].pieces[j].overlapDraw = true;
				}
		int[] OP = new int[oP.size()], Op = new int[op.size()];
		for (int i = 0; i < oP.size(); i++) {
			OP[i] = oP.get(i).intValue();
			Op[i] = op.get(i).intValue();
		}
		overlap(OP, Op);
	}

	public synchronized void overlap(int[] player, int[] piece) {
		if (player.length == 0)
			return;
		if (player.length == 1) {
			setOverlapping(player[0], piece[0], Piece.OVERLAP_INT_NULL);
			return;
		}
		int[] count = { 0, 0, 0, 0 };
		for (int i = 0; i < player.length; i++) {
			count[player[i]]++;
			if (count[player[i]] > 1)
				players[player[i]].pieces[piece[i]].overlapDraw = false;
		}

		for (int i = 0; i < player.length; i++)
			players[player[i]].pieces[piece[i]].overlaps = count[player[i]];

		int over = 0;
		for (int i = 0; i < count.length; i++)
			if (count[i] > 0)
				over++;

		int check = -1, mod = 0;
		for (int i = 0; i < player.length; i++) {
			setOverlapping(player[i], piece[i], over * 10 + (player[i] == check ? mod : ++mod));
			check = player[i];
		}
	}

	public synchronized void six() {
		if (!limitedSix) {
			turn--;
			return;
		}
		sixReset();
		turn--;
		six[0]++;
	}

	public synchronized void sixReset() {
		if (playerTurn[turn % players.length] != six[1]) {
			six[0] = 0;
			six[1] = playerTurn[turn % players.length];
		}
	}

	public synchronized void update(int player, int piece) {
		if (!gameDice.rolled)
			return;
		boolean moved = movePiece(player, piece, gameDice.number + 1);
		if (gameDice.number == 5) {
			six();
			if (six[0] >= 3) {
				if (players[player].pieces[piece].getState() != Piece.STATE_INT_START) {
					setOnStart(player, piece);
					turn++;
					update();
				} else
					turn++;
			} else {
				if (players[player].pieces[piece].getState() == Piece.STATE_INT_START)
					setPlaying(player, piece);
				update();
				rollDice();
			}
		} else if (moved)
			update();

	}

	public synchronized void update() {
		gameDice.rolled = false;
		turn++;
		sixReset();
	}

	public BufferedImage draw() {
		BufferedImage ret = new BufferedImage(boardSizeX, boardSizeY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();
		g.drawImage(boardImage(), 0, 0, null);
		drawDice(g);
		for (int i = 0; i < players.length; i++)
			for (int j = 0; j < players[i].pieces.length; j++)
				drawPiece(g, i, j);

		int i = 0;
		if (playerTurn[turn % players.length] == i++)
			g.drawImage(turnImage, startPlaceSizeX * 0, startPlaceSizeY * 0, null);
		if (playerTurn[turn % players.length] == i++)
			g.drawImage(turnImage, startPlaceSizeX * 2, startPlaceSizeY * 0, null);
		if (playerTurn[turn % players.length] == i++)
			g.drawImage(turnImage, startPlaceSizeX * 0, startPlaceSizeY * 2, null);
		if (playerTurn[turn % players.length] == i++)
			g.drawImage(turnImage, startPlaceSizeX * 2, startPlaceSizeY * 2, null);

		if (!gameDice.rolled)
			g.drawImage(turnImage, startPlaceSizeX * 2 - turnImage.getWidth(),
					startPlaceSizeY * 2 - turnImage.getHeight(), null);
		return ret;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(draw(), 0, 0, null);
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
// add(new LUDO());
// setUndecorated(true);
// setSize(Toolkit.getDefaultToolkit().getScreenSize());
// setVisible(true);
// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// }
//
// public static void main(String[] args) {
// }
// }
