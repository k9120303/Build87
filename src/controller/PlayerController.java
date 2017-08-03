
package controller;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import game.map.Map;
import set.GameSet;
import unit.Character.DIRECTION;
import unit.Entity;
import unit.Food;
import unit.Player;
import unit.PlayerWall;
import view.MapView;

public class PlayerController {
	private Player player;
	private Player otherplayer;
	private MapController mapController;
	// private PlayerWall playerWallPoint[] = new PlayerWall[3] ;

	PlayerController(Player player, Player otherplayer, MapController mapController, int playerNum) {
		this.player = player;
		this.otherplayer = otherplayer;
		this.player.setXNext(this.player.getXpos());
		this.player.setYNext(this.player.getYpos());
		this.mapController = mapController;
		this.mapController.mapView.setLabelIcon(this.player.getXpos(), this.player.getYpos(),
				this.player.getImgPath());

		mapController.mapView.setFocusable(true);
		mapController.mapView.addKeyListener(playerNum == 1 ? new PlayeKeyListener1() : new PlayeKeyListener2());

		Timer bleed = new Timer();
		bleed.schedule(new TimerTask() {
			@Override
			public void run() {
				hurt();
			}

		}, 0, 200);
		Timer Reborn2 = new Timer();
		Reborn2.schedule(new TimerTask() {
			@Override
			public void run() {
				player.setNowSkillTime(player.getNowSkillTime()-1);
				//System.out.println(player.getNowSkillTime());
				;
			}
		},0,1000);
	}

	public Player getPlayer() {
		return player;
	}

	private int checkNextX() {
		DIRECTION direction = player.getDirection();
		if (direction.equals(DIRECTION.UP)) {
			return player.getXpos() - 1;
		} else if (direction.equals(DIRECTION.DOWN)) {
			return player.getXpos() + 1;
		} else {
			return player.getXpos();
		}
	}

	private void destoryWall() throws InterruptedException {
		player.playMusic("蓋牆音樂");
		Thread threadB = new Thread() {
			@Override
			public void run() {
				try {
					int nextX = checkNextX(), nextY = checkNextY();
					if (mapController.map.isDetachableWall(nextX, nextY)) {
						if (mapController.map.isPlayerWall(checkNextX(), checkNextY())) {
							mapController.destoryPlayerWall(checkNextX(), checkNextY(),  player.getDestroyerWallSpeed());
							player.decreasePlayerWAllsCounter(nextX, nextY);
						} else
							mapController.destoryWall(checkNextX(), checkNextY(),  player.getDestroyerWallSpeed());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		threadB.start();
		threadB.join();
	}

	private void addWall() throws InterruptedException {
		if (!isWall()) {
			player.playMusic("蓋牆音樂");
			Thread threadB = new Thread() {
				@Override
				public void run() {
					try {
						if (player.getPlayerWallsCounter().size() == PlayerWall.getMaxWall()) {

							Thread threadC = new Thread() {
								@Override
								public void run() {
									try {
										mapController.destoryPlayerWall(player.getPlayerWallsCounter().get(0).getXpos(),
												player.getPlayerWallsCounter().get(0).getYpos(),
												player.getDestroyerWallSpeed());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							};
							threadC.start();
							threadC.join();
							player.decreasePlayerWAllsCounter(player.getPlayerWallsCounter().get(0).getXpos(),
									player.getPlayerWallsCounter().get(0).getYpos());
						}
						mapController.addPlayerWall(checkNextX(), checkNextY(), player.getConstorWallSpeed());
						player.addPlayerWAllsCounter(checkNextX(), checkNextY());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			threadB.start();
			threadB.join();
		}
	}

	private int checkNextY() {
		DIRECTION direction = player.getDirection();
		if (direction.equals(DIRECTION.LEFT)) {
			return player.getYpos() - 1;
		} else if (direction.equals(DIRECTION.RIGHT)) {
			return player.getYpos() + 1;
		} else {
			return player.getYpos();
		}
	}

	private boolean isWall() {
		return mapController.map.isWall(checkNextX(), checkNextY());
	}

	public void getFood() {

		if (mapController.map.isFood(player.getXpos(), player.getYpos())) {
			player.setHP(player.getHP() + Food.getHpUp());
			mapController.FoodDelete(player.getXpos(), player.getYpos());
		}

	}

	private void hurt() {
		if(player.getIsHurt())
		player.setHP(player.getHP() - 1);
		if(player.getHP()==0)mapController.mapView.setLabelIcon(player.getXpos(), player.getYpos(), player.getImgPath());
	}

	public class PlayeKeyListener1 extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			char input = e.getKeyChar();
			switch (input) {
			case 'w':
				player.setDirection(DIRECTION.UP);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.DOWN);
				break;
			case 's':
				player.setDirection(DIRECTION.DOWN);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.UP);
				break;
			case 'a':
				player.setDirection(DIRECTION.LEFT);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.RIGHT);
				break;
			case 'd':
				player.setDirection(DIRECTION.RIGHT);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.LEFT);
				break;
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (player.getMoveable() == true&&player.getHP()>0) // 判斷有無能力移動
			{
				mapController.mapView.setLabelIcon(player.getXpos(), player.getYpos(), "map/img/floor.jpg"); // 把現在的位子設成地板
				switch (e.getKeyCode()) {

				case KeyEvent.VK_W:
					if (player.getDirection() == DIRECTION.DOWN && !isWall() && player.getIsConfuse())
						player.setXNext(1);
					else if (player.getDirection() == DIRECTION.UP && !isWall())
						player.setXNext(-1); // 下個位子不是牆才移動
					break;

				case KeyEvent.VK_S:
					if (player.getDirection() == DIRECTION.UP && !isWall() && player.getIsConfuse())
						player.setXNext(-1);
					else if (player.getDirection() == DIRECTION.DOWN && !isWall())
						player.setXNext(1);
					break;

				case KeyEvent.VK_A:
					if (player.getDirection() == DIRECTION.RIGHT && !isWall() && player.getIsConfuse())
						player.setYNext(1);
					else if (player.getDirection() == DIRECTION.LEFT && !isWall())
						player.setYNext(-1);
					break;

				case KeyEvent.VK_D:
					if (player.getDirection() == DIRECTION.LEFT && !isWall() && player.getIsConfuse())
						player.setYNext(-1);
					else if (player.getDirection() == DIRECTION.RIGHT && !isWall())
						player.setYNext(1);
					break;

				case KeyEvent.VK_T:
					if (player.getNowSkillTime() == 0) {
						player.skill(mapController.map, otherplayer);
						
					}
					break;

				case KeyEvent.VK_R:
					player.setMoveable(false);// 蓋牆
					Timer Reborn = new Timer();
					Reborn.schedule(new TimerTask() {
						@Override
						public void run() {
							if (isWall() == false) {
								try {
									addWall();
								} catch (InterruptedException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							} else {
								try {
									destoryWall();
								} catch (InterruptedException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}

							}
						}
					}, 1);
					Reborn.schedule(new TimerTask() {
						@Override
						public void run() {
							player.setMoveable(true);
						}

					},  player.getConstorWallSpeed());
					break;
				}
				player.setXpos(player.getXNext());
				player.setYpos(player.getYNext());
				mapController.mapView.setLabelIcon(player.getXpos(), player.getYpos(), player.getImgPath());
			}
			getFood();
		}
	}

	public class PlayeKeyListener2 extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			char input = e.getKeyChar();
			switch (input) {
			case '8':
				player.setDirection(DIRECTION.UP);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.DOWN);
				break;
			case '2':
				player.setDirection(DIRECTION.DOWN);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.UP);
				break;
			case '4':
				player.setDirection(DIRECTION.LEFT);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.RIGHT);
				break;
			case '6':
				player.setDirection(DIRECTION.RIGHT);
				if (player.getIsConfuse())
					player.setDirection(DIRECTION.LEFT);
				break;
			}

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (player.getMoveable() == true&&player.getHP()>0) // 判斷有無能力移動
			{
				mapController.mapView.setLabelIcon(player.getXpos(), player.getYpos(), "map/img/floor.jpg"); // 把現在的位子設成地板
				switch (e.getKeyChar()) {

				case '8':
					if (player.getDirection() == DIRECTION.DOWN && !isWall() && player.getIsConfuse())
						player.setXNext(1);
					else if (player.getDirection() == DIRECTION.UP && !isWall())
						player.setXNext(-1); // 下個位子不是牆才移動
					break;

				case '2':
					if (player.getDirection() == DIRECTION.UP && !isWall() && player.getIsConfuse())
						player.setXNext(-1);
					else if (player.getDirection() == DIRECTION.DOWN && !isWall())
						player.setXNext(1);
					break;

				case '4':
					if (player.getDirection() == DIRECTION.RIGHT && !isWall() && player.getIsConfuse())
						player.setYNext(1);
					else if (player.getDirection() == DIRECTION.LEFT && !isWall())
						player.setYNext(-1);
					break;

				case '6':
					if (player.getDirection() == DIRECTION.LEFT && !isWall() && player.getIsConfuse())
						player.setYNext(-1);
					else if (player.getDirection() == DIRECTION.RIGHT && !isWall())
						player.setYNext(1);
					break;

				case KeyEvent.VK_1:
					if (player.getNowSkillTime() == 0) {
						player.skill(mapController.map, otherplayer);
					}
					break;

				case KeyEvent.VK_0:
					player.setMoveable(false);// 蓋牆
					Timer Reborn = new Timer();
					Reborn.schedule(new TimerTask() {
						@Override
						public void run() {
							if (isWall() == false) {
								try {
									addWall();
								} catch (InterruptedException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							} else {
								try {
									destoryWall();
								} catch (InterruptedException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}

							}
						}
					}, 1);
					Reborn.schedule(new TimerTask() {
						@Override
						public void run() {
							player.setMoveable(true);
						}

					}, player.getConstorWallSpeed());
					break;
				}
				player.setXpos(player.getXNext());
				player.setYpos(player.getYNext());
				mapController.mapView.setLabelIcon(player.getXpos(), player.getYpos(), player.getImgPath());
			}
			getFood();
		}
	}

}