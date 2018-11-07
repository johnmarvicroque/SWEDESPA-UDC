package views;

import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Models;
import models.Users;
import modules.Modules;

public class Hub
{
	/***********************************************
	 * Class Attributes:
	 */
	private ArrayList<Modules> modules;
	private ArrayList<Stage> stages;
	private ArrayList<Users> users;
	private Models model;
	private LoginTerminal login;
	
	/***********************************************
	 * Contructor:
	 */
	public Hub()
	{
		modules = new ArrayList<Modules>();
		stages = new ArrayList<Stage>();
		users = new ArrayList<Users>();
		
		model = new Models();
		model.setHub(this);
	}
	
	/***********************************************
	 * Class Methods:
	 */
	public void setLogin(LoginTerminal login)
	{
		this.login = login;
		this.login.setHub(this);
		this.login.setModels(model);
	}
	
	public void updateAll()
	{
		for(int i = 0; i < modules.size(); i++)
		{
			modules.get(i).update();
		}
	}
	
	public void logout(Users user)
	{
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).getUserName().equals(user.getUserName()))
			{
				users.remove(i);
				modules.remove(i);
				stages.remove(i);
				System.out.println("User successfully logged out!");
				break;
			}	
		}
	}
	
	public boolean openNewModule(Users user)
	{
		boolean doesExist = false;
		
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).getUserName().equals(user.getUserName()))
			{
				System.out.println("User already logged in!");
				doesExist = true;
				break;
			}	
		}
		
		if(doesExist)
		{
			return false;
		}
		else
		{
			int index;
			
			users.add(user);
			index = users.size()-1;
			stages.add(new Stage());
			
			switch(user.getUserType().toUpperCase())
			{
				case "DOCTOR":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/DoctorModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        modules.add(loader.getController());
				        modules.get(index).setUser(user);
				        modules.get(index).setModels(model);
				        modules.get(index).setHub(this);
				        modules.get(index).initiateComponents();
				        modules.get(index).update();			        
				        
				        stages.get(index).setScene(scene);
//				        stages.get(index).initStyle(StageStyle.UNDECORATED);
				        stages.get(index).show();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
					
				case "CLIENT":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/ClientModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        modules.add(loader.getController());
				        modules.get(index).setUser(user);
				        modules.get(index).setModels(model);
				        modules.get(index).setHub(this);
				        modules.get(index).initiateComponents();
				        modules.get(index).update();			        
				        
				        stages.get(index).setScene(scene);
//				        stages.get(index).initStyle(StageStyle.UNDECORATED);
				        stages.get(index).show();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
					
				case "SECRETARY":
					try
					{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/modules/SecretaryModule.fxml"));
				        Scene scene = new Scene(loader.load());
				        
				        modules.add(loader.getController());
				        modules.get(index).setUser(user);
				        modules.get(index).setModels(model);
				        modules.get(index).setHub(this);
				        modules.get(index).initiateComponents();
				        modules.get(index).update();			        
				        
				        stages.get(index).setScene(scene);
//				        stages.get(index).initStyle(StageStyle.UNDECORATED);
				        stages.get(index).show();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
			}
			
			return true;
		}
	}
}
