package edu.ycp.cs320.IslandAdventure.model;

public class Player 
{
	private int score;
	private int health;
	private int stamina;
	private int time;
	private Inventory inventory;
	private Location location;
	
	public int getScore()
	{
		return score;
	}
	public void addScore(int scoreToAdd)
	{
		score = score + scoreToAdd;	// Returns current score plus score to add
	}
	
	public int getHealth()
	{
		return health;
	}
	public void modifyHealth(int healthChange)
	{
		health = health + healthChange;	// Health change can be positive or negative
	}
	
	public int getStamina()
	{
		return stamina;
	}
	public void modifyStamina(int staminaChange)
	{
		stamina = stamina + staminaChange;	// Stamina change can be positive or negative
	}
	
	public int getTime()
	{
		return time;
	}
	public void changeTime(int timeChange)
	{
		time = time + timeChange;	// Time can be from 0 to 24. Each activity takes a # of hours
	}
	
	public void addItem(String item, int amount)
	{
		inventory.addItem(item, amount);
	}
	public Boolean hasItem(String item)
	{
		Integer count = inventory.getItemCount(item);
		if (count != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public int getItemCount(String item)
	{
		return inventory.getItemCount(item);
	}
}
