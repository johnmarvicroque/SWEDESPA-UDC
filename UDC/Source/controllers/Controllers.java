package controllers;

import models.Models;
import models.Users;
import modules.Modules;
import views.ViewsNavigator;

public abstract class Controllers 
{
	protected Modules module;
	protected Users user;
	protected Models model;
	
	public void setModels(Models model) 
	{
		this.model = model;
	}

	public void setModule(Modules module) 
	{
		this.module = module;
	}
	
	public void setUser(Users user)
	{
		this.user = user;
	}

	public void changeView(String type) 
	{
		if(type.equals("Weekly"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.WEEKLY);
    	}
    	else if(type.equals("Daily"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.DAILY);
    	}
    	else if(type.equals("Daily Agenda"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.DAILYAGENDA);
    	}
    	else if(type.equals("Weekly Agenda"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.WEEKLYAGENDA);
    	}
    	else if(type.equals("Finished"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.FINISHED);
    	}
    	else if(type.equals("Upcoming"))
    	{
    		ViewsNavigator.loadView(ViewsNavigator.UPCOMING);
    	}
	}
	
	public void attachModuleToNavigator()
	{
		ViewsNavigator.setModule(module);
	}
}
