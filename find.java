import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

class find 
{
	public static boolean found=false;

	public String getexe(String s){
		int n=s.length();
		for(int i=n-1;i>=0;i--){
			if(s.charAt(i)=='.'){
				return s.substring(i+1,n);
			}
		}
		return "";
	}

	public String getfilename(String s){
		int n=s.length();
		for(int i=n-1;i>=0;i--){
			if(s.charAt(i)=='.'){
				return s.substring(0,i);
			}
		}
		return s;
	}

    public void findFile(String name,File file)
    {
        File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list)
        {
        	String tmpname=getfilename(fil.getName());
            if (fil.isDirectory())
            {
            	if(name.equals(fil.getName())){
            		found=true;
            		System.out.println(fil.getParentFile()+"    "+"type : Directory");
            	}

                findFile(name,fil);
            }
            else if(name.equals(fil.getName())){
            	String tmpex=getexe(fil.getName());
            	if(tmpex.equals("") || tmpex==null){
            		found=true;
            		System.out.println(fil.getParentFile()+"    "+"type : file");	
            	}
            	else{
            		found=true;
            		System.out.println(fil.getParentFile()+"    "+"type : "+tmpex);
            	}
            }
      
            else if(name.equals(tmpname)){
            	String tmpex=getexe(fil.getName());
            	found=true;
            	System.out.println(fil.getParentFile()+"    "+"type : "+tmpex);
            }
        }
    }

    public void find(String name)
    {
    	Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File file=new File(s);
        File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list)
        {
        	String tmpname=getfilename(fil.getName());
        	if (fil.isDirectory())
            {

            	if(name.equals(fil.getName())){
            		found=true;
            		System.out.println(fil.getParentFile()+"    "+"type : Directory");
            	}

                findFile(name,fil);
            }
            else if(name.equals(fil.getName())){
            	String tmpex=getexe(fil.getName());
            	if(tmpex.equals("") || tmpex==null){
            		found=true;
            		System.out.println(fil.getParentFile()+"    "+"type : file");	
            	}
            	else{
            		found=true;
            		System.out.println(fil.getParentFile()+"    "+"type : "+tmpex);
            	}
            }
            else if(name.equals(tmpname)){
            	String tmpex=getexe(fil.getName());
            	found=true;
            	System.out.println(fil.getParentFile()+"    "+"type : "+tmpex);
            }
        }
    }
    public static void main(String[] args) 
    {
        find ff = new find();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the file to be searched.. " );
        String name = scan.nextLine();
        int len=name.length();
        ff.find(name);
        if(found==false){
            System.out.println("file not found");
        }
    }
}