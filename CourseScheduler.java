//Imports java utility and io packages to access all the required methods and classes in the package
import java.util.*;
import java.io.*;


/**
   -This class implements graph coloring backtracking algorithm to schedule the classes in such a way
      that students who need certain courses are not affected by scheduling conflits.
   -It consist of several methods to determine if the solution exists with the user inquired time slots,
      print the full schedule of courses in different time slots, and print the courses that is reachable from the user 
      asked course.
   -The class uses double dimension array to store and use Adjacency matrix created in AdjacencyMatrix class.
   -The class uses array data structure to store the different values about department, number,building, classroom and instructor.
   -The class uses graphColor and promising methods to implement graph coloring algorithm
*/
public class CourseScheduler 
{
   //Declaration and initialization of variables and arrays to be used in the class.
   private int noOfSlots;
   private int noOfCourses;
   private boolean gotSolution;
   private boolean[][] matrixInfo;
   private int vcolor[];
   private String output;
   
   private String[] dept;
   private String[] number;
   private String[] building;
   private String[] room;
   private String[] instructor;
   
   
   /**
      -This constructor accepts the name of the data file as argument.
      -It use the information about the number of courses, department, number, building, room, and instructor
         and adds it in the arrays. It creates AdjacencyMatrix object to form and use boolean adjacency matrix.
      -while loop is go through the file and try-catch statement is used to throw if any exceptions occcur 
      @param filename name of the file 
   */
   public CourseScheduler(String filename)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         noOfCourses = Integer.parseInt(in.nextLine());
         
         dept = new String[noOfCourses];
         number = new String[noOfCourses];
         building = new String[noOfCourses];
         room = new String[noOfCourses];
         instructor = new String[noOfCourses];
         vcolor = new int[noOfCourses];
         int i = 0;
         while(in.hasNextLine())
         {
            Scanner readLine = new Scanner(in.nextLine());
            readLine.useDelimiter(",");
            while(readLine.hasNext())
            {
               dept[i] = readLine.next();
               number[i] = readLine.next();
               building[i] = readLine.next();
               room[i] = readLine.next();
               instructor[i] = readLine.next();
            }
            i++;
         }
         AdjacencyMatrix matrix = new AdjacencyMatrix(dept,number,building,room,instructor);
         this.matrixInfo = matrix.adjacency();
            
      }
      catch(Exception e)
      {
         System.out.println("Some Exception Occured");
      }
   }
   
   
   /**
      -This method implements the graph coloring backtracking algorithm using promising method and 
         boolean adjacency matrix.
      -for loop is used to go through each slots where the courses can be placed. If the course is promising in
         that particular slot it is allocated in that slot. The algorithm works recursively until end of the course is 
         reached. Then required output for all the courses on different slots is prepared using another for loop.
      -A if condition is used at the beginning of the method to make sure the program stops once all the courses get the slots. 
      -The method also sets the value of gotSolution true if solution exists with the noOfSlots given.
      @param vertex course that is checked and put on slot if promising
   */
   
   private void graphColor(int vertex)
   {
      
      if(vcolor[noOfCourses-1]!=0)
      {
         return;
      }
      
      for(int slot = 1; slot<=noOfSlots; slot++)
      {
         if(promising(vertex,slot))
         {
            vcolor[vertex]= slot;
            if((vertex+1)<noOfCourses)
            {
               graphColor(vertex+1);
            }
            else
            {
               for (int i = 1; i<=noOfSlots;i++)
               {
                  output+=("\nTime " + i + ":\n");
                  for (int j=0; j<noOfCourses; j++)
                  {
                     if (i==vcolor[j])
                     {
                        output+=(dept[j]+" "+number[j]+ " " +building[j] +" "+room[j].substring(1)+" "+instructor[j]+"\n");
                     }
                  }
               }
               gotSolution = true;
               return;
            }
         }
      }
   }
   
   
   /**
      -This method checks if the vertex is promising in a particular slot. It is called by graphColor method for execution
      -It uses for loop to go through all the courses if they already exist on that slot and to check their boolean value 
         with the given vertex in the matrix
      @param vertex the course to be checked for promising
      @slot the slot to be checked for the feasibility of the course there
      @return boolean value whether the vertex is promising or not
   */
   private boolean promising(int vertex, int slot)
   {
      for(int i = 0; i<noOfCourses;i++)
      {
         if(matrixInfo[vertex][i] && slot == vcolor[i])
         {
            return false;
         }
      }
      return true;
   }
   
   
   /**
      -This method checks if it is possible to develop a schedule with m slots.
      -All the required variables are set to their default value and graphColor method is called
         to check if solution exists and return required value
      @param m number of slots
      @return boolean value whether the solution exists
   */
   public boolean solutionExists(int m)
   {
      for(int i = 0; i<this.noOfCourses;i++)
      {
         vcolor[i]=0;
      }
      output="";
      this.gotSolution=false;
      noOfSlots = m;
      graphColor(0);
      return gotSolution;
   }
   
   
   /**
      -This method returns the schedules of the courses with their slots if possible.
      -All the required variables are set to their default value and graphColor method is called
         to get the value of output if it the solution exists with m slots.
      -if else statement is used for returning in case the solution doesn't exists
      @param m number of slots
      @return schedule of the courses with the slots or message about no solution
   */
   public String getSchedule(int m)
   {
      this.gotSolution=false;
      noOfSlots = m;
      output="";
      for(int i = 0; i<this.noOfCourses;i++)
      {
         vcolor[i]=0;
      }
      
      noOfSlots = m;
      graphColor(0);
      
      if (gotSolution == false)
      {
         return ("No solution with "+ noOfSlots + " slots");
      }
      
      else
      {
         return output;
      }
      
   }
   
   
   /**
      -This method returns the number and lists of courses reachable from the courses given as argument.
      -Arraylist is used to add the adjacent courses and display the output
      -for loop is used to add the adjacents courses using information from adjacency Matrix
      -while loop is used with for loop to add the courses that are adjacent from the adjacent courses
      @param from course from which reachable courses are to be found
      @return reachable courses from the given course 
   */
   public String reachable(String from)
   {
      List<Integer> adjacents = new ArrayList<Integer>();
       
      for(int i = 0; i<noOfCourses; i++)
      {
         if(((from.substring(0,4)).equals(dept[i])) && ((from.substring(5,9)).equals(number[i])))
         {
            adjacents.add(i);
         }
      }
      
      int size =0;
      while((size<adjacents.size()))
      {
         for(int i=0;i<noOfCourses;i++)
         {
            int a = adjacents.get(size);
            if(matrixInfo[a][i])
            {
               if(!(adjacents.contains(i)))
               {
                  adjacents.add(i);
               }
            }
         }
         size++;
      }
      
      
      String output=(adjacents.size()+" courses:");
      
      for(int index  : adjacents)
      {
         output+=(", "+dept[index]+ " " +number[index]);
      }
      
      return (output.replace(":,",":"));
      
   }
}