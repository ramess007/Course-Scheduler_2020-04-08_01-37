/**
   -This class is used to form double dimensional boolean array of adjacency matrix
   -It consists of adjacency method where nested for loop and if conditions are used to 
      assign the boolean value to cells of matrix
   -This class returns the adjacency matrix to CourseSchedular class
*/
public class AdjacencyMatrix
{
   //Declaration and initialization of variables and arrays to be used in the class.
   private int noOfCourses;
   private String[] dept;
   private String[] number;
   private String[] building;
   private String[] room;
   private String[] instructor;
   
   
   /**
      -This constructor accepts the arrays related to department, number, building, room and instructor
         and initilizes the respective arrays for this class
      @param dept department of the course
      @param number course id number
      @param building course taught building
      @param room course taught room
      @param instructor course's instructor
   */
   public AdjacencyMatrix(String[]dept,String[]number,String[]building,String[]room,String[]instructor)
   {
      this.dept = dept;
      this.number = number;
      this.building = building;
      this.room = room;
      this.instructor = instructor;
      this.noOfCourses= dept.length;
   }
   
   
   /*
      -This method assigns boolean value to the double dimenstion adjacency matrix and returns the matrix.
      -It uses nested for loop and if else conditional statement to assign the boolean value
      -The matrix cell is true only if two courses have same instructor, same room, and they are in same
         discipline and are both numbered 4... or 3...
      -@return double dimension adjacency matrix
   **/
   protected boolean[][] adjacency()
   {
      boolean[][] createMatrix = new boolean[noOfCourses][noOfCourses];
      for (int i = 0; i < noOfCourses; i++)
      {
         for (int j = 0; j < noOfCourses; j++)
         {
            
            
            if((instructor[i].equals(instructor[j])) || (room[i].equals(room[j])))
            {
               createMatrix[i][j] = true;
            }
            if((dept[i].equals(dept[j])) && (((number[i].charAt(0)=='3')&&(number[j].charAt(0)=='4')) || ((number[j].charAt(0)=='3')&&(number[i].charAt(0)=='4'))||((number[j].charAt(0)=='3')&&(number[i].charAt(0)=='3'))||((number[j].charAt(0)=='4')&&(number[i].charAt(0)=='4'))))
            
            {
              createMatrix[i][j] = true;      
            }
         }
      }
      return createMatrix;
   }
}