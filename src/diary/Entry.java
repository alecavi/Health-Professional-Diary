package diary;

/**
 * 
 * @author Scott
 *
 */
import java.util.Scanner;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class Entry implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String info;
	String date;
	ZonedDateTime startTime;
	ZonedDateTime endTime;
	String treatmentType;
	Boolean valid = false;
	
	
	Appointment root;
	Appointment testApp;
	static Entry testEntry;
	
	Scanner s = new Scanner(System.in);
	
	public Entry(String date, double startTime, double endTime, String treatmentType) 
	{
		testApp=new Appointment(date, startTime, endTime, treatmentType);
		testEntry= new Entry(date, startTime, endTime, treatmentType);
	}
	
    public static void main(String[] args)
    {
    	testEntry.test();
    }

    public void test() 
    {
    	getUserAdd();
    	printTree(getAppointment());
    	
    }
    
	public Appointment getAppointment() 
	{
		return root;
	}
	
	
		public void getUserAdd()
		{
			System.out.println("Input the user date");
			String date=s.nextLine();
			
			System.out.println("Input the user date");
			double startTime=s.nextDouble();
			
			System.out.println("Input the user date");
			double endTime=s.nextDouble();
			
			System.out.println("Input the user date");
			String treatmentType=s.nextLine();
			
			addAppointment(new Appointment(date, startTime, endTime, treatmentType),getAppointment());
		
		}
		
		public void addAppointment(Appointment newNode, Appointment root) 
		{
			

			if(newNode.startTime>root.startTime)
			{
				if(root.right!=null) 
				{
					addAppointment(newNode, root.right);
				}
				else
				{
					root.right=newNode;
				}
			}
				else
				{
					if(root.left!=null) 
					{
						addAppointment(newNode, root.left);
					}
					else 
					{
						root.left=newNode;
					}
				}
			}
		
		public void edit() 
		{
			System.out.println("Enter the date of the appointment you want to edit");
			String editChosen= s.nextLine();
			
			System.out.println("Enter the changes you want to make");
			String date=s.nextLine();
			
			root=getAppointment();
			
			root.date=date;
			System.out.println(root.date + root.startTime + root.endTime + root.treatmentType);
			
			
		}
		
		public void getDelete() 
		{
			System.out.println("Pick the date of the appointment you want to delete");
			String delete=s.nextLine();
			delete(delete, root=testApp.getAppointment());
		}
		
		
		public static Appointment delete(String delete, Appointment root) 
		{
			
			if (root == null) 
			{
				return null;
			}
			
			if (delete==root.getDate()) 
			{
				root=null;
			}
			return root;
		}
		
		public void printTree(Appointment root) 
		{
			if(root==null) 
			{
				return;
			}
			printTree(root.left);
			System.out.println(root.info);
			printTree(root.right);
		}

		public void find(String date,Appointment root) 
		{
			if(root==null)
			{
				return;
			}
			
			
				if(date==root.date) 
			{
				System.out.println(root.date + root.startTime + root.endTime + root.treatmentType);
				valid=true;
				return;
			}
				find(date,root.left);
				find(date, root.right);
		}
		
		
	}


