package edu.ycp.cs320.IslandAdventure.controller;

import edu.ycp.cs320.IslandAdventure.persist.DatabaseProvider;
import edu.ycp.cs320.IslandAdventure.persist.DerbyDatabase;
import edu.ycp.cs320.IslandAdventure.persist.IDatabase;

import java.util.ArrayList;

import edu.ycp.cs320.IslandAdventure.model.*;

public class GameEngine 
{
	
	//if action go to action controller
	
	//return resulting string to index servlet
	
	private IDatabase db    = null;

	public GameEngine() {
		
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	public boolean insertNewAccountIntoDatabase(Account account) {
		// Check to see if username is already taken
		Account found = null;
		found = db.retrieveAccount(account.getUsername());
		if (found != null) {
			System.out.println("Failed to insert account for <" + account.getUsername() + "> because username is already taken");
			
			return false;
		}
		// Create new account
		Integer account_id = db.insertAccountIntoAccountTable(account);
		if (account_id > 0)
		{
			System.out.println("New account (ID: " + account_id + ") successfully added to accounts table: <" + account.getUsername() + ">");
			
			Player player = account.getPlayer();
			Skills skills = player.getSkills();
			Location location = player.getLocation();
			// Create Player using account_id
			int player_id = db.addPlayer(account.getUsername(), player.getScore(), player.getHealth(), 
					player.getStamina(), player.getTime(), location.getX(), location.getY(), 
					location.getZ(), account_id, skills.getWoodCuttingXP(), skills.getFishingXP(), 
					skills.getCombatXP(), skills.getCraftingXP());
			
			System.out.println("New Player added, Player_id: <" + player_id + ">");
			// Create Rooms using accountID
			//db.insertRoomsIntoDatabase(account_id, account.getUsername(), account.getRooms());
			db.loadInitialData(account_id, account.getUsername());
			// Create Objects using account_id
			return true;
		}
		else
		{
			System.out.println("Failed to insert new account (ID: " + account_id + ") into accounts table: <" + account.getUsername() + ">");
			
			return false;
		}
	}
	
	public boolean verifyAccountInDatabase(Account account) {
		Account found = null;
		found = db.retrieveAccount(account.getUsername());
		if (found != null){
			if (found.getUsername().equals(account.getUsername()) && found.getPassword().equals(account.getPassword())) {
				System.out.println("Account varified for " + account.getUsername());
				return true;
			}else{
				System.out.println("Invalid username and/or password.");
				return false;
			}
		}else{
			System.out.println("Account not found for " + account.getUsername());
			return false;
		}
	}
	
	// Method for getting account_id
	public Integer getAccountID(String username){
		Integer account_id = null;
		
		account_id = db.getAccountIdFromDatabase(username);
		System.out.println("GameEngine >> Account_id # <" + account_id + "> found from <" + username + ">");
		
		return account_id;
	}
	
	// Method for saving player information to database
	public boolean updatePlayerInDatabase(int account_id, Player player) {
		return db.updatePlayerInDatabase(account_id, player);
	}
	
	// Method to load a player when re-entering the game
	public Player loadPlayer(int account_id){
		return db.getPlayer(account_id);
	}
	
	public boolean updateMapInDatabase(int account_id, Account account){
		return db.updateMapInDatabase(account_id, account);
	}
	
	public ArrayList<Room> loadMap(int account_id){
		return db.loadMapFromDatabase(account_id);
	}
	
	public Boolean insertNewItemIntoDatabase(Account account, int account_id, Item item, Integer amount, Integer damage) 
	{
		Location location = item.getLocation();
		Integer inventoryItem = 0;
		if (account.getPlayer().getInventory().getItemCountFromString(item.getName()) > 0)
		{
			inventoryItem = 1;
		}
		String name = item.getName();
		String description = item.getDescription();
		Integer uses = item.getUses();
		Integer x = location.getX();
		Integer y = location.getY();
		Integer z = location.getZ();

		Boolean added = db.insertItemIntoDatabase(account_id, inventoryItem, name, description, 
				uses, amount, x, y, z, damage);
		return added;
	}
	
	public Account updateItemList(Account account, int account_id) //Uploads account with items in db
	{
		account = db.updateItemList(account_id, account);
		return account;
	}
	
	public boolean updateItemsList(int account_id, Account account){
		return db.updateItemsInDatabase(account_id, account);
	}
	
	public void moveItemInventory(int account_id, int inventoryItem, String name) // Changes account items from inventory to map or vice versa
	{
		db.moveItemInventory(account_id, inventoryItem, name);
	}
	public void updateItemAmount(int account_id, String name, Integer amount) //Changes amount of item
	{
		db.updateItemAmount(account_id, name, amount);
	}
	
	public void updateItemLocation(int account_id, String name, int x, int y, int z){
		db.updateItemLocation(account_id, name, x,y,z);
	}
	
	public Boolean insertEnemyIntoDatabase(Account account, int account_id, Enemy enemy) 
	{
		Location location = enemy.getLocation();
		String name = enemy.getName();
		String description = enemy.getDescription();
		Integer health = enemy.getHealth();
		Integer damage = enemy.getDamage();
		Integer x = location.getX();
		Integer y = location.getY();
		Integer z = location.getZ();

		Boolean added = db.insertEnemyIntoDatabase(account_id, name, description, health, damage, x, y, z);
		return added;
	}
	
	public Account updateEnemiesList(int account_id, Account account)
	{
		account =  db.updateEnemyList(account_id, account);
		return account;
	}
	public Boolean removeEnemy(int account_id, Enemy enemy) //Changes amount of item
	{
		Location location = enemy.getLocation();
		String name = enemy.getName();
		String description = enemy.getDescription();
		Integer x = location.getX();
		Integer y = location.getY();
		Integer z = location.getZ();
		
		Boolean added = db.removeEnemy(account_id, name, description, x, y, z);
		return added;
	}
	public void loadInitialData(int account_id, Account account){
		db.loadInitialData(account_id, account.getUsername());
	}
}
