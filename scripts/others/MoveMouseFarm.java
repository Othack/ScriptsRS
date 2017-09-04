package scripts.others;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.zulrahUtilities.ConditionZul;
import scripts.zulrahUtilities.PositionZul;

public final class MoveMouseFarm {

	public final ArrayList<List<java.awt.Point>> listeP = new ArrayList<List<java.awt.Point>>();
	public SleepJoe sleepJoe = new SleepJoe();
	public final static int NB_FILES = 4;
	public long last = 0;
	private boolean outHover = false;

	/** Offsets en x (-50 a 50) **/
	static int[] positionX = { 17, 1, 5, 6, -4, -16, -8, 0, 1, 5, 14, 15, 1, -13, -6, -9, 9, 11, 11, -1, -13, -14, -11,
			0, -1, -18, -14, -9, -6, 8, 8, 8, -2, 10, 13, 0, -10, -16, -18, -24, -24, -14, -9, -3, -3, -3, 5, 12, 11,
			-2, -7, -9, -9, 4, 9, 8, -3, -10, -16, -27, -34, -25, -14, -8, -10, -31, -33, -33, -28, -16, -1, 13, 15, 24,
			28, 38, 37, 33, 29, 16, -4, -20, 18, 24, 38, 37, 21, 5, -10, -16, -2, 20, 27, 27, 22, 22, 25, 28, 33, 29,
			15, 10, 14, 7, -7, -2, 1, -21, -29, -35, -36, -37, -38, -36, -32, -29, -10, -31, -45, -43, 35, 11, -8, 11,
			25, 32, 23, 15, 10, 2, -12, -20, -32, -41, -41, -41, -41, -41, -39, -15, -16, -16, -16, -12, 12, -11, -11,
			12, 15, 13, 13, 21, 22, 22, 22, 25, 23, 21, 14, 12, 3, -10, -19, -21, -26, -24, -17, -13, -3, 0, -7, -10,
			-12, -6, 6, 7, 10, 11, -4, -1, 1, 0, 4, 6, 8, 10, 2, 1, -2, 9, 12, 12, 10, 2, -7, -13, -17, -8, 4, 10, 2,
			-3, 0, 0, 0, 0, 0, 0, 0, -2, 12, -3, -7, 1, 6, 16, 5, -15, -18, -8, 1, 15, 23, 11, -6, -1, -9, -15, -15, -8,
			-8, -19, 1, 6, 10, -7, -8, 6, 4, 3, 1, -9, -21, -24, -28, -28, -37, -37, -27, -34, -29, -19, -31, -20, -25,
			-27, -33, -12, -21, -27, -22, -11, 13, 23, 23, 27, 29, 31, 33, 43, 42, 41, 38, 38, 27, 31, 35, 35, 35, 34,
			30, 37, 40, 40, 40, 33, 31, 35, 37, 44, 12, -11, -19, -27, -34, -41, -20, 0, 12, 18, 5, 0, 10, 18, 18, 14,
			-11, -16, -18, -27, -25, -25, -21, -23, -18, -19, -13, -5, -21, -20, 10, 21, 11, 12, 20, 12, 14, 15, 10, -3,
			-4, 7, 19, 23, 11, 2, 2, 10, 11, -8, -12, -11, -3, 14, 13, -6, -22, -7, -2, -5, 13, -3, 9, -2, 3, -2, 6, 0,
			4, 8, 4, 4, 2, 3, 3, 0, 3, 4, 4, 6, 6, 10, 3, -4, -19, -21, -14, -9, -9, -18, -11, -9, -7, -5, -15, -16,
			-13, -3, 6, 7, -3, -9, -7, 5, -21, -37, -44, -33, -36, -32, -32, -28, -19, -17, -11, -18, -18, -11, -11,
			-22, -15, -6, -1, 5, 17, 26, 31, 22, 9, 13, 17, 22, 33, 35, 33, 35, 40, 42, 35, 28, 22, 8, 0, -16, -16, -2,
			4, 15, 30, 36, 37, 32, 9, -1, -17, -13, 6, 12, 12, 6, 3, 0, 6, 11, 11, 13, 23, 26, 19, 25, 25, 21, 21, 29,
			36, 30, 30, 31, 32, 34, 37, 30, 14, -5, -17, -19, -24, -30, -31, -25, -28, -29, -30, -33, -38, -42, -42,
			-36, -35, -34, -29, -30, -30, -29, -30, -33, -36, -37, -38, -38, -37, -35, -31, -25, -8, 6, 9, 23, 24, 17,
			10, 10, 14, 14, 17, 21, 19, 19, 27, 30, 34, 37, 37, 38, 33, 27, 27, 40, 40, 38, 3, 2, 2, 2, -23, -13, -2,
			20, 19, 19, 19, 20, 15, 7, -3, -1, 8, 11, 16, 16, 9, 9, 0, -4, -12, -16, -17, -15, -4, -3, -9, -9, -13, -14,
			-17, -17, -17, -17, -17, -17, -29, -9, -6, 4, 4, 8, 1, 0, -11, -13, -20, -20, -20, -22, -19, -14, -16, -17,
			-10, -6, -9, 8, 12, 13, 17, 19, 19, 19, 19, 21, 21, 21, 21, 22, 21, 19, 18, 17, 15, 15, 15, 12, 3, 2, -4,
			-4, -2, 0, -1, -3, -2, 1, 1, -2, -9, -18, -30, -32, -32, -27, -16, -2, -9, -11, -11, -15, -15, -8, 3, 4, 8,
			8, 11, 7, 8, 8, 8, 3, 3, 2, 8, 10, 10, -11, -6, 2, 3, -6, -9, -13, -4, 3, 19, 12, 12, -4, -4, -2, -6, -18,
			-5, 7, 30, 23, 14, -1, -12, -13, -18, -21, -14, 4, 11, 23, 10, 1, -13, -16, -3, 27, 18, 3, -2, -9, -7, -5,
			-24, -22, -3, 10, 8, -1, -2, 16, 13, 10, 1, 2, 2, -5, -16, -4, 5, -1, 0, 0, -2, -2, -8, 15, -11, -3, 2, 2,
			4, 3, -7, 3, 8, 17, 12, 5, 4, -12, -16, -29, -27, -19, -16, -29, -30, -36, -32, -31, -31, -15, -11, -29,
			-46, -46, -36, 40, 40, 43, 46, 43, 37, 44, 21, 15, 9, -3, -4, 6, 11, 17, 7, -2, 15, 9, 23, 24, 22, 15, -2,
			-12, -38, -36, -31, -28, -23, -13, 3, 12, -4, -17, -3, 12, 13, -5, -14, -15, 7, 12, -7, -6, -15, -21, -33,
			-27, -21, -24, -37, -33, -26, -26, -24, -25, -18, -21, -21, -21, -24, -22, -22, -22, -22, -22, -12, -12,
			-19, -24, -17, -14, -14, -15, -1, -2, -7, -7, -7, -5, -4, -3, -2, -5, -5, -2, -2, -6, -6, -14, -17, -26,
			-24, -11, -1, 13, 26, 21, 20, 8, 8, 1, 0, 4, 4, 5, 5, 7, 18, 12, 12, 21, 22, 18, 18, 18, 18, 15, 16, 12, 24,
			19, 13, 17, 17, 7, 6, -1, -18, -22, -9, 5, 2, -18, -10, -2, -9, -8, -29, -6, -8, -6, -21, -22, -21, -9, -25,
			-20, -13, -5, -40, -21, -11, -30, -17, -19, -12, -1, 10, 22, 34, 31, 26, 15, 25, 25, 30, 30, 14, 14, 6, 2,
			-5, -12, -22, -30, -39, -39, -30, -29, -29, -29, -1, -1, 2, 2, 12, 15, 15, 9, -3, -3, -3, 13, 5, 0, -14,
			-20, -17, -19, -39, -39, -37, -37, -28, -28, -31, -36, -36, -27, -15, 5, 23, 31, 32, 34, 36, 33, 29, 19, 6,
			10, 18, 41, 41, 41, 41, 41, 39, 31, 35, 36, 36, 27, 23, 28, 10, 4, 3, 14, 18, 14, 5, 3, 4, 4, 7, 12, 8, -4,
			-13, -13, -9, -9, -11, -15, -33, -30, -23, -28, -24, -22, -23, -19, -19, -21, -22, -26, -26, -32, -32, -37,
			-37, -42, -39, -38, -37, -36, -33, -28, -28, -28, -28, -28, 8, 10, 16, 16, 16, 16, 21, 21, 22, 24, 21, 15,
			14, -8, -12, -1, -1, -7, 11, 11, -2, -10, -13, -1, 3, 11, 11, -8, -8, -3, 3, 3, 3, 11, 17, 4, 1, 1, -16,
			-16, -13, -10, -10, -10, 12, 10, -4, -4, -10, -8, 10, 10, -48, 5, -2, -2, 10, 12, 17, 26, 16, 14, 14, -6,
			-7, -11, 4, 15, 25, 27, 19, 16, 17, 12, 12, 3, 3, 7, 6, 6, 1, 6, 6, 0, -1, 15, 14, -10, 1, -4, -7, 8, 20,
			13, 8, 4, 8, 19, 18, 6, 2, 6, 7, 10, 4, -5, -10, -9, 4, 10, 10, 5, 2, -6, 8, 16, 18, 10, 4, -4, 9, 2, -9,
			-15, -30, -24, -16, -9, -2, -2, -1, 6, 9, 6, 4, -4, -10, -13, -17, -16, -5, 0, -8, 20, 8, 1, 4, 7, 6, 8, 1,
			7, 4, 15, 9, 7, 5, 0, 3, -3, -5, -8, -4, -6, -11, 4, 5, 7, 12, 9, 5, -4, -7, 2, 9, 2, 0, 9, 6, 1, 1, 1, -1,
			-4, -11, -17, -23, -17, -1, 15, 7, 3, -2, -9, 3, 16, 16, 7, 2, 1, 2, 8, 20, 23, 20, 18, 12, 3, -8, -22, -36,
			-38, -35, -36, -31, -14, 0, 19, 27, 30, 32, 32, 32, 36, 41, 42, 37, 25, 5, -16, -19, -30, -42, -42, -38,
			-26, -16, -15, -17, -15, -14, -7, 1, 12, 12, 12, 12, 12, 8, 2, -2, -1, -1, -1, -10, -8, 4, 8, 5, 6, -11,
			-23, -26, -9, 0, 5, 17, 17, 19, 8, -3, -14, -11, 26, 28, 15, -4, -9, -9, 5, 10, 11, 7, -15, -18, -15, 3, 9,
			4, 2, -12, 3, 8, 11, 7, -8, 0, 2, 13, 9, 4, -6, -5, 4, 4, 3, -3, -2, 4, 4, 3, 1, 19, 8, 7, 3, 3, 6, 6, 6, 6,
			4, 4, -8, 1, 2, -18, -13, -15, -32, -21, -6, -5, 0, 2, 4, 13, 12, 16, 21, 32, 23, 19, 21, 28, 30, 35, 36,
			32, 22, 42, 39, 28, 29, 42, 21, 21, 4, 12, -22, -17, 3, -8, -19, -48, -32, -35, -46, -29, -20, -18, -15,
			-12, 6, 1, -6, 0, 1, 2, -2, -3, 1, 9, 4, 3, 8, -8, -9, -9, -9, -16, -11, -8, -4, -8, -24, -35, -21, -2, 6,
			4, -9, -20, -20, -11, -9, 3, 17, 8, 1, -3, -5, 0, 7, 7, -14, -10, 4, 13, 13, 0, -1, -1, -1, -1, -12, -27,
			-35, -35, -25, 2, 7, 15, 22, 28, 28, 10, 0, 11, 42, 37, 37, 36, 23, 3, -1, -20, -33, -33, -45, -37, -33,
			-13, -2, 5, 16, 24, 33, 38, 40, 40, 39, 30, 16, -17, 3, -1, -4, 12, 15, -7, -4, 43, 48, 44, 12, -2, -7, 17,
			25, 35, 35, 30, 12, 2, 0, -7, -17, -15, -13, -10, -10, -10, -28, -25, -15, 0, 0, 6, 6, -5, -5, -5, -3, 4,
			13, 14, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, -10, -15, -14, 11, 21, 14, 14, 13, 10, 16, 17, 23, 25, 28, 28, 25, 26,
			26, 4, -6, -25, -32, -29, -14, -14, 3, 8, -4, -11, -11, -1, 0, 4, 4, 18, 18, 14, 14, 3, 1, -2, -14, -16,
			-16, -2, 9, 13, 8, 1, 19, 18, 18, 10, -1, 11, 26, 26, 13, -5, 2, 0, 0, -3, -18, -21, -21, -21, -14, 0, 14,
			25, 28, 11, 0, 10, 11, 19, 29, 40, 30, 22, 24, 12, -1, -12, -27, -30, -35, -39, -40, -28, -34, -11, -5, 4,
			2, -3, 2, 11, 18, 22, 31, 34, 34, 30, 21, 7, 1, -2, -8, -8, -8, 10, -10, -12, -12, -16, -23, -39, -33, -25,
			-25, -33, -41, -42, -43, -41, -33, -16, 2, 17, 28, 35, 37, 37, 24, 9, 5, 5, 20, 2, 14, 22, 5, -5, 0, 1, 1 };

	/** Offsets en y (-50 a 50) **/
	static int[] positionY = { 6, -6, 5, 23, 14, 8, 0, -12, 1, 12, 3, -3, -15, -2, 16, 10, -8, -8, -6, 2, -4, -8, -13,
			-14, 12, 17, 14, -2, -7, 4, 9, 10, -12, -14, -14, 4, 14, 15, 5, -6, -11, -19, -19, -17, -17, -17, -22, -11,
			3, 11, 15, 5, 5, -1, 2, 13, 20, 19, 16, 36, 31, 27, 20, 11, -2, -20, -23, -23, -24, -26, -28, -32, -32, -33,
			-33, -15, 0, 18, 24, 29, 30, 36, 37, 37, 38, 28, 33, 34, 35, 35, 28, 26, 16, 2, -8, -19, -16, -16, 1, 8, 12,
			9, -6, -21, -11, 4, 5, -4, 8, 22, 13, -2, -7, -18, -30, -31, -42, -38, -43, -33, -32, -41, -37, -21, -6, 18,
			29, 38, 37, 37, 38, 38, 35, 38, 33, 19, 10, 2, -1, 21, 24, 24, 6, 5, 2, 10, 10, 7, 7, 2, -5, -14, -15, -15,
			-15, -4, 4, 9, 15, 16, 20, 15, 12, 7, 1, -2, -7, -8, 4, 7, 11, 8, 3, -1, 11, 25, 2, -4, -11, 6, -3, -5, -5,
			-5, -5, -5, -18, -19, -13, -4, -11, -11, -13, -19, -18, -6, 10, 12, 12, 8, -3, 2, 4, 4, 4, 4, 4, 4, 4, -7,
			15, 17, -4, -5, 29, 14, 3, 3, 29, 10, -3, 17, -3, -12, 3, -8, -12, -13, -12, -23, -23, -27, -21, -21, -29,
			-34, -34, -27, -27, -36, -36, -35, -32, -31, -19, -4, 11, 19, 28, 13, 14, 22, 13, 16, 21, 18, 5, -21, -36,
			-24, -25, -18, -17, -25, -26, -24, -13, 0, 5, -6, -11, -24, -34, -37, -21, -11, -5, -20, -20, -7, 8, 26, 20,
			13, 10, 4, 9, 12, 6, -3, 36, 35, 36, 38, 42, 37, 12, 7, 0, -13, -19, -7, 2, 0, 0, 14, 12, 0, -9, -19, -7, 1,
			7, 12, -5, -12, -19, -17, 7, 8, 28, 11, 9, 10, 21, 12, 4, -2, -13, -3, 6, 15, -1, -11, -8, 15, 24, -3, -26,
			-11, 6, 9, 6, 24, 22, 21, 6, -15, -16, 22, -2, 10, -13, 24, -7, -14, -4, -17, -11, -9, -11, -11, -19, -12,
			-11, -7, -10, -10, -17, -16, -16, -17, -13, -10, -10, -19, -8, 3, 14, 16, -7, -6, 3, 5, -16, -19, -13, 1, 3,
			-8, -14, -3, 12, 22, 4, -12, -20, -7, -23, -25, -31, -40, -40, -29, -22, -31, -36, -29, -28, -35, -35, -34,
			-39, -39, -37, -39, -40, -40, -36, -44, -44, -42, -33, -21, -2, 12, 30, 31, 38, 40, 38, 37, 38, 42, 37, 40,
			43, 42, 45, 40, 22, 15, 2, -2, 17, 21, 31, 30, 17, 13, 12, 14, 22, 26, 27, 29, 23, 23, 27, 22, 14, -2, 27,
			16, 19, 31, 33, 33, 40, 30, 23, 24, 30, 30, 31, 35, 36, 28, 28, 25, 22, 22, 28, 31, 34, 26, 25, 23, 23, 18,
			0, -8, -14, -16, -20, -29, -34, -34, -29, -18, -4, 7, -10, -18, -26, -28, -28, -23, -20, -17, -26, -26, -25,
			-27, -26, -22, -22, -12, -18, -30, -31, -28, -19, -13, -16, -29, -28, -7, -5, 2, 16, 12, 12, 12, 4, 5, 12,
			23, 2, -1, -1, 12, 27, 21, 6, 6, 13, 13, 20, 20, 15, 15, 19, 18, 14, 18, 21, 18, 20, 20, -3, -3, 0, 0, 0,
			-1, -1, -14, -14, -14, 9, -7, -7, -22, -22, -26, -21, -20, -19, -20, -8, -6, 0, 2, -6, -11, -7, -1, -3, 4,
			31, 30, 27, 23, 15, 13, 9, 8, 15, 12, -4, -9, -9, 7, 0, -4, -5, -15, -18, -18, -3, 5, 9, 9, 3, 3, 2, 2, 2,
			0, 0, -2, -11, -11, -8, 6, 17, 5, -14, -22, -19, -8, 14, -4, -12, 1, -5, -11, -7, -7, 8, 11, -4, -4, 1, 1,
			4, 11, 1, -6, -11, 1, 13, -4, -8, -4, 9, 22, 20, 8, 0, -6, 16, 3, 3, 15, 16, 12, 3, 13, 21, 7, -17, -19,
			-27, -24, -21, -28, -29, -28, 23, 17, -5, -40, -1, -5, -19, -32, -20, -15, -14, 24, 13, -6, 2, 39, 14, 1,
			-22, -17, 18, 8, 8, -2, 31, 32, 30, 24, 6, 17, -12, 6, -17, 13, -11, -6, 3, 3, -5, -10, 25, 2, -6, 20, 4,
			24, -13, 12, 27, 13, -2, 15, 16, 5, 31, 15, 2, 0, 14, 25, 25, 31, 24, 33, 33, 37, 47, 46, 45, -9, -42, -43,
			-33, -34, -39, -25, 45, 41, 3, 0, 3, 13, 12, 18, 21, 35, 41, 48, 48, 42, 42, 42, 32, 32, 33, 32, 45, 26, 18,
			2, -14, 4, 18, -1, 4, 10, -2, 21, 21, 25, 22, 38, 21, 3, 15, 5, 26, 27, 13, 5, -11, -16, -10, 4, 7, -11,
			-11, 0, -9, -19, -19, -17, -14, -11, -11, -11, -11, -11, -16, -17, -26, -8, 2, 0, -2, -8, -10, -9, -6, -5,
			-5, -3, -5, -5, -5, -8, -8, -18, -13, -5, -4, 0, -4, -14, -14, -13, 7, 18, 19, 8, 8, -1, -1, -3, -3, -1, -1,
			1, 8, 5, -1, -3, 10, 18, 18, 14, 9, 9, 13, 14, 9, 9, 13, 8, 12, 21, 21, 7, 7, 14, 23, 30, 29, 30, 19, 30,
			28, 24, 30, 30, 43, 24, 28, 22, 25, 26, 26, -2, 20, 24, 22, 15, 52, 28, 21, 26, 28, 39, 27, 23, 18, 11, -4,
			-8, -14, -3, 1, 1, 14, 14, -21, -21, -22, -27, -32, -33, -26, -19, -6, -1, 19, 20, 20, 20, -3, -3, 1, 1, -2,
			-1, -1, 4, -5, -5, -5, -35, -38, -36, -24, -22, -28, -35, -27, -14, 8, 9, 5, 1, 0, 4, 4, 18, 26, 27, 21, 10,
			5, -5, -15, -22, -24, -24, -16, 6, 16, 25, 24, 20, 11, 0, -4, -3, 4, 2, -7, -18, -22, -30, -28, -3, 16, 17,
			5, -2, 20, 26, 25, 18, 1, -11, -9, 3, 22, 17, 6, 24, 27, 25, 3, 9, 14, 22, 17, 26, 20, 29, 22, 17, 17, 19,
			20, -6, -6, 5, 6, 13, 15, 13, 6, -6, -9, -14, -10, -10, -10, -10, 9, 1, -6, -6, -7, -7, 7, 13, -6, -14, -10,
			0, 3, 7, -1, 5, 7, 20, 12, 11, -5, -1, 1, 13, 11, -1, -1, 6, 7, 2, -1, -1, -1, -4, -4, -6, -5, -5, -36, -36,
			-23, -16, 5, 15, 2, -3, -12, -13, 16, 17, 0, 0, -6, -1, 12, 12, 12, 12, 19, 5, 3, 23, 23, 35, 28, 30, 36,
			37, 34, 22, 14, 3, -4, -14, -14, 0, 0, -22, -12, -11, -5, -9, -9, -1, 31, 19, 3, 22, 15, -9, 5, -2, -9, -15,
			-11, 8, 22, 8, 4, 1, 2, 8, 8, -4, -5, -8, 1, 15, 9, 1, -6, -12, -9, 13, 8, -11, -14, -20, -18, -9, -37, -28,
			-21, -17, 4, 14, 15, 18, 14, 13, 9, 2, 1, -10, -10, -4, -6, -7, -3, 22, 7, 6, 21, 14, -6, 1, 8, 4, 1, -1, 6,
			1, 2, -6, -6, -7, -5, -1, -3, -12, -9, -13, -19, -19, -17, -16, -5, -5, 2, 4, 8, 17, 27, 24, 19, 10, 10, 11,
			16, 10, 15, 16, 20, 21, 20, 12, 7, 5, 6, 17, 1, 4, 4, 5, 3, 8, 4, -1, -8, -9, -15, -13, -14, -17, -23, -28,
			-31, -37, -37, -35, -29, -21, -8, 8, 17, 28, 36, 36, 27, 23, 8, 0, 0, 17, 27, 35, 36, 37, 36, 35, 38, 32, 3,
			-8, -11, -19, -19, -8, 1, -5, 6, 10, 0, 3, 4, 4, 4, -4, -8, -14, -11, -9, -9, -9, 14, 12, 13, 14, 2, -12,
			-9, 3, 16, 20, 16, 16, 6, 4, -7, -11, -12, -7, 6, 14, 4, -12, -9, -3, 3, 10, 6, -2, -8, -5, 12, 12, 11, 3,
			-4, -4, 7, 12, 8, -3, -8, -5, 7, 11, 4, 1, -2, 7, 10, 1, 1, 1, 7, 3, -4, -7, -11, -8, 3, 9, 10, 16, 10, 10,
			10, 10, 14, 3, 3, 4, -9, 4, 16, 10, 0, -10, -15, -9, 6, 4, -19, -19, -21, -33, -28, -27, -38, -24, -12, -4,
			-5, -21, -30, -30, -14, -3, -23, -25, -12, -9, -23, -15, -14, -3, -25, -19, -4, 0, 19, 28, 37, 23, 12, 9,
			10, -8, -23, -15, -5, -12, -22, -15, 2, 8, 4, 3, 12, 18, 15, 13, 2, 3, 9, 9, 15, 15, 24, 19, 16, 0, -4, -12,
			-2, 16, 30, 6, -6, -19, -29, -29, -32, -34, -40, -26, -11, 1, 5, 13, 6, -11, -11, 16, 15, 6, 2, 2, 9, 9, 9,
			9, 9, 19, 30, 18, -17, -26, -5, 14, 9, -17, -27, -28, -38, -38, -30, -32, -28, -14, 9, 27, 43, 39, 41, 31,
			31, 19, 8, 8, 39, 36, 36, 37, 24, 11, -5, -16, -31, -38, -38, -34, -48, -32, -23, -12, 14, 16, -45, -44,
			-14, 5, 22, -1, 9, 19, 38, 40, 34, 33, 20, 9, 15, 18, 3, 18, 21, 14, 8, -5, -5, -6, -5, 17, 20, 20, 0, 0, 4,
			4, 4, 8, -2, -12, -12, -4, -4, -4, -4, -4, -2, -2, -2, -2, -2, 4, 24, 23, 21, 20, 11, 11, -3, 4, 21, 19, 5,
			5, 8, 8, 13, -3, -3, 11, -9, -23, 0, 5, 2, 2, 5, -26, -10, 5, 5, 3, 2, -2, -2, -8, -9, -11, -11, -12, -12,
			-10, -3, -3, -3, 18, 23, 18, 17, 7, 4, 16, 23, 8, 15, 26, 18, 18, 26, 23, 13, 6, 0, -6, -12, -23, -25, 14,
			22, -7, 1, -10, -24, -19, -29, -28, -21, -8, -4, 15, 9, 23, 45, 36, 38, 41, 36, 21, 8, -19, -30, -37, -37,
			-6, -1, 12, -8, -10, -3, 6, -18, -27, -36, -37, -21, -10, -7, 9, 15, 15, 7, 7, 6, -2, -29, -20, 4, 10, 19,
			7, 4, -1, -1, -20, -21, -25, 2, 14, 29, 35, 38, 37, 30, 3, -14, -28, -43, -37, -11, -3, -10, -7, 2, -23,
			-21, 2, 7, 5, 5 };

	public MoveMouseFarm() {
		loadDataNormal();
	}

	/**
	 * Load les data de mouse
	 */
	@SuppressWarnings("resource")
	private void loadDataNormal() {
		for (int i = 0; i < 14; i++) {

			String filePath = "C:\\Users\\joel_\\Documents\\AutoClickPro\\Data\\file" + i + ".txt";
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(filePath);
				br = new BufferedReader(fr);
				br = new BufferedReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sCurrentLine;
			listeP.add(new ArrayList<java.awt.Point>());
			try {
				while ((sCurrentLine = br.readLine()) != null) {
					java.awt.Point pt = new Point();
					pt.x = Integer.parseInt(sCurrentLine.substring(0, sCurrentLine.indexOf(",")));
					pt.y = Integer
							.parseInt(sCurrentLine.substring(sCurrentLine.indexOf(",") + 1, sCurrentLine.length()));
					listeP.get(listeP.size() - 1).add(pt);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void checkDataOk(){
		List<Point> listePts = listeP.get(1);
		for (Point pt : listePts){
			General.println(pt);
		}
	}
	
	// ***************************** PLAY MOUSE
	// ************************************************************************
	public void playMouse(Point endP, String upText, ConditionZul c, double speed) {

		// Courbe
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);

		// Liste
		final List<Point> listePoints = listeP.get(randomCourbe);
		int sizeListe = listeP.get(randomCourbe).size();

		Point firstFile = new Point(listePoints.get(0));
		Point lastFile = new Point(listePoints.get(sizeListe - 1));
		Point realLast = new Point(endP);
		Point realStart = Mouse.getPos();
		double ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
				/ Math.sqrt(Math.pow(lastFile.x, 2) + Math.pow(lastFile.y, 2));
		double angle = Functions.angleBetween2Lines(firstFile, lastFile, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((sizeListe) / (speed * Math.pow(ratio, 1) * sizeListe / 8));
		if (result == 0) {
			result = 8;
			General.println("WTF dude");
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point lastP = Mouse.getPos();
		for (int i = 0; i < sizeListe; i += result) {
			Point pt = listePoints.get(i);
			if (pt.getX() == 0 && pt.getY() == 0) {
				continue;
			}
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;
			pt = Functions.rotate(pt, angle);
			pt.x = (int) (pt.x * ratio);
			pt.y = (int) (pt.y * ratio);
			pt.x += sX;
			pt.y += sY;
			if (!Functions.isPointsEqual(Mouse.getPos(), lastP)) {
				checkMouseStop(c);
				if (!outHover) {
					playMouse(endP, upText, c, speed);
				}
				break;
			}
			Mouse.hop(pt);
			lastP = pt;
			General.sleep(8);
			if (c != null && c.checkCondition()) {
				outHover = true;
				break;
			}
			if (upText != null && Game.isUptext(upText)) {
				break;
			}
		}
	}

	private void checkMouseStop(ConditionZul c) {
		Point lastP = Mouse.getPos();
		while (true) {
			General.sleep(100);
			if (c!=null && c.checkCondition()) {
				outHover = true;
				break;
			}
			if (Functions.isPointsEqual(Mouse.getPos(), lastP)) {
				break;
			} else {
				lastP = Mouse.getPos();
			}

		}

	}

	public void playMouseTime(Point endP, String upText, ConditionTime c, double speed) {

		// Courbe
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);

		// Liste
		final List<Point> listePoints = listeP.get(randomCourbe);
		int sizeListe = listeP.get(randomCourbe).size();

		Point firstFile = new Point(listePoints.get(0));
		Point lastFile = new Point(listePoints.get(sizeListe - 1));
		Point realLast = new Point(endP);
		Point realStart = Mouse.getPos();
		double ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
				/ Math.sqrt(Math.pow(lastFile.x, 2) + Math.pow(lastFile.y, 2));
		double angle = Functions.angleBetween2Lines(firstFile, lastFile, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((sizeListe) / (speed * Math.pow(ratio, 0.75) * sizeListe / 8));
		if (result == 0) {
			result = 8;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point lastP = Mouse.getPos();
		Point toGo = new Point();
		for (int i = 0; i < sizeListe; i += result) {
			Point pt = listePoints.get(i);
			if (pt.getX() == 0 && pt.getY() == 0) {
				continue;
			}
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;
			pt = Functions.rotate(pt, angle);
			pt.x = (int) (pt.x * ratio);
			pt.y = (int) (pt.y * ratio);
			pt.x += sX;
			pt.y += sY;
			if (!Functions.isPointsEqual(Mouse.getPos(), lastP)) {
				checkMouseStop(c);
				playMouseTime(endP, upText, c, speed);
				break;
			}
			Mouse.hop(pt);
			lastP = pt;
			General.sleep(8);
			if (c != null && c.checkCondition()) {
				outHover = true;
				break;
			}
			if (upText != null && Game.isUptext(upText)) {
				break;
			}
		}

	}
	

	private void checkMouseStop(ConditionTime c) {
		Point lastP = Mouse.getPos();
		while (true) {
			General.sleep(100);
			if (c!=null && c.checkCondition()) {
				outHover = true;
				break;
			}
			if (Functions.isPointsEqual(Mouse.getPos(), lastP)) {
				break;
			} else {
				lastP = Mouse.getPos();
			}

		}

	}

	// *********************** PLAY MOUSE FOLLOW
	// ******************************************************************************************************************

	public void playMouseFollowTile(RSTile tile, double speed) {
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		Point first = listeP.get(randomCourbe).get(0);
		Point last = listeP.get(randomCourbe).get(listeP.get(randomCourbe).size() - 1);
		Point realLast = null;
		Point realStart = null;
		realLast = tile.getPosition().getHumanHoverPoint();
		realStart = Mouse.getPos();
		double ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
				/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
																							// sometimes
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			ratio = 0.10;
		}
		int result = (int) Math.round(
				(listeP.get(randomCourbe).size()) / (speed * Math.pow(ratio, 1) * listeP.get(randomCourbe).size() / 8));
		if (result == 0) {
			result = 8;
		}
		int ptSpeed = 0;
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point pt = null;
		for (int i = 0; i < listeP.get(randomCourbe).size(); i++) {
			if (ptSpeed % 50 == 0) {

				if (tile != null && tile.getPosition().isOnScreen()
						&& !Functions.pointsProches(tile.getPosition().getHumanHoverPoint(), realLast, 10)) {
					i = listeP.get(randomCourbe).size() / 2;
					realLast = tile.getPosition().getHumanHoverPoint();
					realStart = Mouse.getPos();
					first = new Point(listeP.get(randomCourbe).get(i).x, listeP.get(randomCourbe).get(i).y);
					ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
							/ Math.sqrt(Math.pow(last.x - listeP.get(randomCourbe).get(i).x, 2)
									+ Math.pow(last.y - listeP.get(randomCourbe).get(i).y, 2));
					angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
					sX = realStart.x;
					sY = realStart.y;
					offX = listeP.get(randomCourbe).get(i).x;
					offY = listeP.get(randomCourbe).get(i).y;
					if (ratio == 0) {
						ratio = 0.10;
					}
					result = (int) Math.round((listeP.get(randomCourbe).size())
							/ (speed * Math.pow(ratio, 1) * listeP.get(randomCourbe).size() / 8));
					if (result == 0) {
						result = 8;
					}
				}
			}
			if (ratio == 0) {
				ratio = 1;
			}
			if (ptSpeed % (result) == 1) {
				pt = new Point(listeP.get(randomCourbe).get(i).x, listeP.get(randomCourbe).get(i).y);
				pt.x = pt.x - offX;
				pt.y = pt.y - offY;
				pt = Functions.rotate(pt, angle);
				pt.x = (int) (pt.x * ratio);
				pt.y = (int) (pt.y * ratio);
				pt.x += sX;
				pt.y += sY;
				Mouse.hop(pt);
				General.sleep(8);
			}
			ptSpeed++;
			if (Functions.pointsProches(Mouse.getPos(), realLast, 10)) {
				break;
			}
		}
	}
	// ********************** HUMAN MOUSE
	// MOVE*******************************************************************

	/** Fonction qui retourne un point dans le box **/
	public java.awt.Point randomPoint(java.awt.Point point, int recx, int recy) {

		int randomLocation = Functions.generateRandomInt(1, 1455);
		java.awt.Point newPoint = new Point();

		int deltaX = positionX[randomLocation];
		int deltaY = positionY[randomLocation];

		deltaX = deltaX + Functions.generateRandomInt(-2, 2);
		deltaY = deltaY + Functions.generateRandomInt(-2, 2);

		newPoint.x = point.x + deltaX * recx / 50;
		newPoint.y = point.y + deltaY * recy / 50;
		return newPoint;

	}

	/**
	 * Function moves mouse dans une box pass�e en parametre (utilis� pour
	 * clicker sur un objet surtout)
	 **/
	public void humanMouseMove(TBox box, double speed) {
		java.awt.Point thePointMid = new Point();
		thePointMid.x = box.x1 + box.sizeX() / 2;
		thePointMid.y = box.y1 + box.sizeY() / 2;

		thePointMid = randomPoint(thePointMid, box.sizeX() / 2, box.sizeY() / 2);
		playMouse(thePointMid, null, null, speed);
	}

	/** Fonction qui va checker les stats **/
	public void checkStats(int stat, int multiplier) {
		humanMouseMove(new TBox(566, 174, 590, 198), 1);
		fastClick(1, 1);

		if (stat == 0) {
			stat = Functions.generateRandomInt(1, 4);
		}

		switch (stat) {
		case 1:// "Ranged":
			humanMouseMove(new TBox(557, 305, 604, 324), 1);
			sleepJoe.sleepHumanDelay(2, 0, 2000);
			break;
		case 2:// "Defence":
			humanMouseMove(new TBox(555, 274, 604, 293), 1);
			sleepJoe.sleepHumanDelay(2, 0, 2000);
			break;
		case 3:// "Magic":
			humanMouseMove(new TBox(564, 364, 604, 388), 1);
			sleepJoe.sleepHumanDelay(2, 0, 2000);
			break;
		case 4:// "Hitpoints":
			humanMouseMove(new TBox(616, 209, 665, 231), 1);
			sleepJoe.sleepHumanDelay(2, 0, 2000);
			break;
		}

		FTAB(27, 1);
	}

	/** Fonction qui change de tab selon la cle pass� en param�tre **/
	public void FTAB(int tabNumber, double multiplier) {
		Keyboard.sendPress(KeyEvent.CHAR_UNDEFINED, tabNumber);
		sleepJoe.sleepHumanDelay(0.2, (int) (30 * multiplier), (int) (160 * multiplier));
		Keyboard.sendRelease(KeyEvent.CHAR_UNDEFINED, tabNumber);
		sleepJoe.sleepHumanDelay(0.1, (int) (15 * multiplier), (int) (80 * multiplier));
	}

	/** Retourne true si le sleep ne rencontre pas la condition d'arret **/
	public boolean sleepHumanDelayCondition(double multiplier, int min, int max, ConditionZul c) {

		int sleepTime = sleepJoe.sleepHumanDelayOut(multiplier, min, max);
		for (int i = 0; i < 10; i++) {
			if (!c.checkCondition()) {
				General.sleep(sleepTime / 10);
			} else {
				return false;
			}
		}
		return true;
	}

	/** Fonctio qui sort la mouse hors de l'�cran **/
	public void outMouseLeft(double multiplier) {
		humanMouseMove(new TBox(-100, -100, -100, 700), 1);
	}

	// ***********************************CLICK FUNCTIONS
	// **********************************************************************
	/** Fonction qui spam click **/
	public void spamClick(TBox box, int numberClicks, int range) {
		// Detruit uniformite des clicks.
		int begin = range;
		for (int i = 0; i < begin; i++) {
			if (Functions.generateRandomInt(1, 100) > 40) {
				range--;
			}
		}
		int numberSpam = Functions.generateRandomInt(numberClicks - range, numberClicks + range);
		for (int i = 0; i < numberSpam; i++) {
			fastClick(1, 0.7);
			sleepJoe.sleepHumanDelay(0.15, 20, 80);
		}
	}

	/** Fonction qui clique rapidement **/
	public void fastClick(int button, double multiplier) {
		java.awt.Point point = Mouse.getPos();
		sleepJoe.sleepHumanDelay(0.02, 1, 20);
		Mouse.sendPress(point, button);
		sleepJoe.sleepHumanDelay(0.2, (int) (30 * multiplier), (int) (160 * multiplier));
		Mouse.sendRelease(point, button);
		sleepJoe.sleepHumanDelay(0.02, 1, 20);
	}

	/** Fonction qui clique rapidement **/
	public void fastClickBank(int button, double multiplier) {
		java.awt.Point point = Mouse.getPos();
		sleepJoe.sleepHumanDelay(0.25, 1, 200);
		Mouse.sendPress(point, button);
		sleepJoe.sleepHumanDelay(0.2, (int) (30 * multiplier), (int) (160 * multiplier));
		Mouse.sendRelease(point, button);
		sleepJoe.sleepHumanDelay(0.25, 1, 200);
	}

	// ****************************************PLAY MOUSE FOLLOW
	// NPC******************************************************
	/**
	 * Suit une tile (tres specifique quand plusieurs meme ids)
	 * 
	 * @param tile
	 * @param tile1x
	 * @param tile1y
	 * @param tile2x
	 * @param tile2y
	 * @param ID
	 * @return
	 * @throws InterruptedException
	 */
	public void playMouseFollowNPC(RSNPC npc, String uptext, double speed) {
		int randomCourbe = Functions.generateRandomInt(0, NB_FILES);
		List<Point> listePts = listeP.get(randomCourbe);
		Point first = listePts.get(0);
		Point last = listePts.get(listePts.size() - 1);
		Point realLast = null;
		Point realStart = null;
		RSModel npcMod = npc.getModel();
		realLast = npcMod.getCentrePoint();
		if (!Functions.isOnScreen(realLast)) {
			realLast = npcMod.getHumanHoverPoint();
		}
		realStart = Mouse.getPos();
		double ratio = Math.sqrt(Math.pow(realLast.x - realStart.x, 2) + Math.pow(realLast.y - realStart.y, 2))
				/ Math.sqrt(Math.pow(last.x - first.x, 2) + Math.pow(last.y - first.y, 2)); // null
																							// sometimes
		double angle = Functions.angleBetween2Lines(first, last, realStart, realLast);
		if (ratio == 0) {
			return;
		}
		int result = (int) Math.round((listePts.size()) / (speed * Math.pow(ratio, 1) * listePts.size() / 8));
		if (result == 0) {
			result = 8;
			General.println("Sup2");
			return;
		}
		int sX = realStart.x;
		int sY = realStart.y;
		int offX = 0;
		int offY = 0;
		Point pt = null;
		Point toGo = new Point();
		for (int i = 0; i < listePts.size(); i += result) {
			
			//Point de la liste. Moins l'offset pour avoir le vecteur (point de debut).
			pt = listePts.get(i); 
			pt.x = pt.x - offX;
			pt.y = pt.y - offY;
			
			//Rotation du point
			pt = Functions.rotate(pt, angle);
			toGo.x = (int) (pt.x * ratio);
			toGo.y = (int) (pt.y * ratio);
			toGo.x += sX;
			toGo.y += sY;
			if (Functions.isOnScreen(toGo)){
			Mouse.hop(toGo);
			} else {
				General.println("Not on screen point trying to hop");
				General.println("Angle : "+angle+"Pt : "+pt+"Pt realLast : "+realLast+"Pt realStart : " +realStart+ "Ratio : " +ratio+ "Result: "+result);
			}
			General.sleep(8);
			if (npc != null && npc.isOnScreen() && !Functions.pointsProches(npcMod.getCentrePoint(), realLast, 5)) {
				playMouseFollowNPC(npc, uptext, speed);
				break;
			}
			
		}
	}
//*****************************HOVER********************************************************************************
	/**
	 * Hover mouse dans une box et arrete quand la condition est true.
	 * 
	 * @param box
	 * @param c
	 */
	public void hoverMouse(TBox box, ConditionZul c, double speed, boolean yes) {
		
		if (c.checkCondition()){
			General.sleep(10,20);
			return;
		}
		
		int xmin = -box.sizeX() / 2 + box.middlePoint().x;
		int xmax = box.sizeX() / 2 + box.middlePoint().x;
		int ymin = -box.sizeY() / 2 + box.middlePoint().y;
		int ymax = box.sizeY() / 2 + box.middlePoint().y;
		int x = Functions.generateRandomInt(-box.sizeX() / 2, box.sizeX() / 2) + box.middlePoint().x;
		int y = Functions.generateRandomInt(-box.sizeY() / 2, box.sizeY() / 2) + box.middlePoint().y;

		Point pt = new Point(x, y);
		playMouse(pt, null, c, speed);
		Point prevPoint = new Point(pt);

		do {
			if (Functions.generateRandomInt(1, 10) == 2 && !c.checkCondition()) {
				sleepJoe.sleepHumanDelayCondition(6, 0, 6000, c);
			} else if (!c.checkCondition()) {
				pt = Mouse.getPos();
				TBox box2 = new TBox(pt, box.sizeX() / 2, box.sizeY() / 2);
				x = Functions.generateRandomInt(-box2.sizeX() / 2, box2.sizeX() / 2) + box2.middlePoint().x;
				y = Functions.generateRandomInt(-box2.sizeY() / 2, box2.sizeY() / 2) + box2.middlePoint().y;
				pt = new Point(x, y);
				double angle = Functions.angleBetween2Lines(Mouse.getPos(), prevPoint, Mouse.getPos(), pt);

				if ((angle < 60 || angle > 300) && x > xmin && x < xmax && y > ymin && y < ymax) {
					playMouse(pt, null, c, 5);
				}
				prevPoint = new Point(pt);
			}
			if (!outHover && c.checkCondition()) {
				outHover = true;
			}
		} while (!outHover);
		outHover = false;
		if (yes) {
			hoverMouse(new TBox(Mouse.getPos(),100), new ConditionTime(sleepJoe.sleepHumanDelayOut(1, 1, 1000)), 5, false);
		}

	}

	/**
	 * Hover mouse dans une box et arrete quand la condition est true.
	 * 
	 * @param box
	 * @param c
	 */
	public void hoverMouseZulrah(TBox box, ConditionZul c, double speed, boolean yes, PositionZul pAfter,
			boolean switchGearAfter, boolean switchPrayAfter, boolean prayMage) {
		int xmin = -box.sizeX() / 2 + box.middlePoint().x;
		int xmax = box.sizeX() / 2 + box.middlePoint().x;
		int ymin = -box.sizeY() / 2 + box.middlePoint().y;
		int ymax = box.sizeY() / 2 + box.middlePoint().y;
		int x = Functions.generateRandomInt(-box.sizeX() / 2, box.sizeX() / 2) + box.middlePoint().x;
		int y = Functions.generateRandomInt(-box.sizeY() / 2, box.sizeY() / 2) + box.middlePoint().y;

		Point pt = new Point(x, y);
		playMouse(pt, null, c, speed);
		Point prevPoint = new Point(pt);
		RSTile tileToGo = Functions.returnMiddleTile(pAfter.tile1, pAfter.tile2);
		Point pointTileNext = Projection.tileToScreen(tileToGo, 0);
		do {
			// //***********
			// if (CheckZulrah.getPercentageDone() > 80 ) {
			// if (!pAfter.checkPlayerOk()){
			// if (tileToGo.isOnScreen()){
			// General.println("hover next point");
			// hover(new TBox(pointTileNext, 20), c, 2);
			// } else {
			// General.println("hover next location");
			// hover(HoverBox.get(2), c, 2);
			// }
			// } else {
			// if (switchPrayAfter){
			// if (!GameTab.TABS.PRAYERS.isOpen()) {
			// Functions.FTAB(116, 1);
			// }
			// if (prayMage){
			// hover(HoverBox.get(25),c,2);
			// } else {
			// hover(HoverBox.get(26),c,2);
			// }
			// }
			// if (switchGearAfter){
			// hover(HoverBox.get(24),c,2);
			// }
			// }
			// } else if (Functions.getHealth() < 58 &&
			// Functions.percentageBool(90)) {
			// if (!GameTab.TABS.INVENTORY.isOpen()) {
			// Functions.FTAB(27, 1);
			// }
			// if (InventoryZulrah.getNearestShark() != null) {
			// hover(new TBox(InventoryZulrah.getNearestShark().getArea()), c,
			// 2);
			// }
			// } else if (Functions.getPrayer() < 7 &&
			// Functions.percentageBool(90)) {
			// if (!GameTab.TABS.INVENTORY.isOpen()) {
			// Functions.FTAB(27, 1);
			// }
			// if (InventoryZulrah.getNearestRestore() != null) {
			// hover(new TBox(InventoryZulrah.getNearestRestore().getArea()), c,
			// 2);
			// }
			// }
			// //*************
			if (Functions.generateRandomInt(1, 10) == 2 && !c.checkCondition()) {
				sleepJoe.sleepHumanDelayCondition(6, 0, 6000, c);
			} else if (!c.checkCondition()) {
				pt = Mouse.getPos();
				TBox box2 = new TBox(pt, box.sizeX() / 2, box.sizeY() / 2);
				x = Functions.generateRandomInt(-box2.sizeX() / 2, box2.sizeX() / 2) + box2.middlePoint().x;
				y = Functions.generateRandomInt(-box2.sizeY() / 2, box2.sizeY() / 2) + box2.middlePoint().y;
				pt = new Point(x, y);
				double angle = Functions.angleBetween2Lines(Mouse.getPos(), prevPoint, Mouse.getPos(), pt);

				if ((angle < 60 || angle > 300) && x > xmin && x < xmax && y > ymin && y < ymax) {
					playMouse(pt, null, c, 5);
				}
				prevPoint = new Point(pt);
			}
			if (!outHover && c.checkCondition()) {
				outHover = true;
			}
		} while (!outHover);
		outHover = false;
		if (yes) {
			hoverMouse(new TBox(0, 0, 700, 300), new ConditionTime(sleepJoe.sleepHumanDelayOut(0.5, 1, 600)), 2, false);
		}

	}

	/**
	 * Hover mouse dans une box et arrete quand la condition est true.
	 * 
	 * @param box
	 * @param c
	 */
	public void hover(TBox box, ConditionZul c, double speed) {
		int xmin = -box.sizeX() / 2 + box.middlePoint().x;
		int xmax = box.sizeX() / 2 + box.middlePoint().x;
		int ymin = -box.sizeY() / 2 + box.middlePoint().y;
		int ymax = box.sizeY() / 2 + box.middlePoint().y;
		int x = Functions.generateRandomInt(-box.sizeX() / 2, box.sizeX() / 2) + box.middlePoint().x;
		int y = Functions.generateRandomInt(-box.sizeY() / 2, box.sizeY() / 2) + box.middlePoint().y;

		Point pt = new Point(x, y);
		playMouse(pt, null, c, speed);
		Point prevPoint = new Point(pt);

		do {
			if (Functions.generateRandomInt(1, 10) == 2 && !c.checkCondition()) {
				sleepJoe.sleepHumanDelayCondition(4, 0, 4000, c);
			} else if (!c.checkCondition()) {
				pt = Mouse.getPos();
				TBox box2 = new TBox(pt, box.sizeX() / 2, box.sizeY() / 2);
				x = Functions.generateRandomInt(-box2.sizeX() / 2, box2.sizeX() / 2) + box2.middlePoint().x;
				y = Functions.generateRandomInt(-box2.sizeY() / 2, box2.sizeY() / 2) + box2.middlePoint().y;
				pt = new Point(x, y);
				double angle = Functions.angleBetween2Lines(Mouse.getPos(), prevPoint, Mouse.getPos(), pt);

				if ((angle < 60 || angle > 300) && x > xmin && x < xmax && y > ymin && y < ymax) {
					playMouse(pt, null, c, 5);
				}
				prevPoint = new Point(pt);
			}
			if (!outHover && c.checkCondition()) {
				outHover = true;
			}
		} while (!outHover);
	}

	public void hoverMouse(TBox box, ConditionZul c) {
		hoverMouse(box, c, 2, true);
	}

	/**
	 * Hover mouse dans une box et arrete quand la condition est true.
	 * 
	 * @param box
	 * @param c
	 */
	public void hoverMouse(TBox box, ConditionTime c, double speed, boolean yes) {
		int xmin = -box.sizeX() / 2 + box.middlePoint().x;
		int xmax = box.sizeX() / 2 + box.middlePoint().x;
		int ymin = -box.sizeY() / 2 + box.middlePoint().y;
		int ymax = box.sizeY() / 2 + box.middlePoint().y;
		int x = Functions.generateRandomInt(-box.sizeX() / 2, box.sizeX() / 2) + box.middlePoint().x;
		int y = Functions.generateRandomInt(-box.sizeY() / 2, box.sizeY() / 2) + box.middlePoint().y;
		Point pt = new Point(x, y);
		playMouseTime(pt, null, c, speed);
		Point prevPoint = new Point(pt);

		do {

			if (Functions.generateRandomInt(1, 10) == 2) {
				sleepJoe.sleepHumanDelayConditionTime(6, 0, 6000, c);
			} else {
				pt = Mouse.getPos();
				TBox box2 = new TBox(pt, box.sizeX() / 2, box.sizeY() / 2);
				x = Functions.generateRandomInt(-box2.sizeX() / 2, box2.sizeX() / 2) + box2.middlePoint().x;
				y = Functions.generateRandomInt(-box2.sizeY() / 2, box2.sizeY() / 2) + box2.middlePoint().y;
				pt = new Point(x, y);
				double angle = Functions.angleBetween2Lines(Mouse.getPos(), prevPoint, Mouse.getPos(), pt);

				if ((angle < 60 || angle > 300) && x > xmin && x < xmax && y > ymin && y < ymax) {
					playMouseTime(pt, null, c, 5);
				}
				prevPoint = new Point(pt);
			}
			if (!outHover && c.checkCondition()) {
				outHover = true;
			}
		} while (!outHover);
		outHover = false;
		if (yes) {
			hoverMouse(new TBox(0, 0, 700, 300), new ConditionTime(sleepJoe.sleepHumanDelayOut(0.5, 1, 600)), 2, false);
		}

	}

}
