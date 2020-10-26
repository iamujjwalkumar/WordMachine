import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Generating
{
static double Total_Word;
static double Comp=-1.0;
static int Meter=0;
static String filename="";
JFrame Temp;
	Generating(Boolean Repeat)
	{
		//Creating Frame
		Temp=new JFrame("Generating PassList..");
		Temp.setSize(450,250);
		Temp.setVisible(true);
		Temp.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
		Temp.setLayout(null);
		Temp.setResizable(false);
		Temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Heading:
		JLabel Heading=new JLabel("Please Wait...");
		Heading.setBounds(150,20,120,40);
		Temp.add(Heading);
		JLabel Detail=new JLabel("Data Is Being Written In File");
		Detail.setBounds(80,80,240,40);
		Temp.add(Detail);
	        new Creater().Start(); 
	}
	Generating(){}
 public void Finish()
 {
	if(Generating.Comp!=Generating.Total_Word)
		JOptionPane.showMessageDialog(Temp,"WordList is Ready!!","Alert",JOptionPane.WARNING_MESSAGE);

 }
 public void Error()
 {	
  JOptionPane.showMessageDialog(Temp,"Some Error Has Occured!!\nTry Again!","Alert",JOptionPane.WARNING_MESSAGE);
 }
}

class Creater
{
Boolean F=true;
Boolean Flag[];
static int count=1;
static String DATA[]=new String[1000];
static int top=-1;
	void Start()
	{
	int Locker[]=new int[App.L_O_P];
	if(App.Fixed.length()!=0)
	Flag=new Boolean[App.Fixed.length()];

	for(int i=0;i<App.L_O_P;i++)
		Locker[i]=0;
	Locker[0]=-1;

	while((++Generating.Comp)<Generating.Total_Word)
	   {
		String temp="";
		Locker[0]++;
		temp="";
		F=true;

	//Initialising The Flags:
	for(int i=0;i<App.Fixed.length();i++)
		Flag[i]=false;

	 //Locker Code:	
	 for(int i=0;i<App.L_O_P;i++)
		if(Locker[i]==App.FinalKey.length())
		 { Locker[i]=0; if(i!=App.L_O_P-1) Locker[i+1]++; }
		else break;


   	 //Code To Write data Make Combinations:
		//Adding data to string:
		for(int i=0;i<App.L_O_P;i++)
			temp+=App.FinalKey.charAt(Locker[i]);
		
		//To check for Fixed Values in string:
		if(App.Fixed.length()!=0)
			for(int j=0;j<App.Fixed.length();j++)
			for(int i=0;i<temp.length();i++)
				if(temp.charAt(i)==App.Fixed.charAt(j))
				Flag[j]=true;

		//Now Getting The Values Of Flags:
		for(int i=0;i<App.Fixed.length();i++)
		if(Flag[i]==false)
			{ F=false; break; }

	  if(F&&!App.Rpt)
	  {
		//Checking the string for non-repeating things:
		for(int i=0;i<temp.length()-1;i++)
		for(int j=i+1;j<temp.length();j++)
		if(temp.charAt(i)==temp.charAt(j))
			{ F=false; i=temp.length(); break;}

	  }

		//If there is all values in the string then print
		if(F){
			DATA[++top]=temp;
			if(top==DATA.length-1)
				Write_To_File();
		     }
		
		//After Finishing:
		if(Generating.Comp+1==Generating.Total_Word)
		  { Write_To_File(); new Generating().Finish(); } 
	}

 }
 public void Write_To_File()
 {
	int Temp_top=-1;
	while(++Temp_top<=top)
	{
	//code to write into file!!
	System.out.print(DATA[Temp_top]+" ---> "+(count++)+"\n");

	File file = new File(Generating.filename+".txt");
	/*	FileWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new FileWriter(file, true);
			fr.write(DATA[Temp_top]+"\n");

			//Need to Clear The Buffer
			//if(Generating.Comp%100==0)
			// { fr.flush(); try{Thread.sleep(50);}catch(Exception e){}}

		} catch (IOException e) {
		 new Generating().Error();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		
try{		//If file is not present it will create
		if(!file.exists()) file.createNewFile();

		FileWriter fw=new FileWriter(file,true);
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(DATA[Temp_top]+"\n");

		bw.close();
   }catch(Exception w){ 
			//new Generating().Error();
			// Generating.filename+="1";
			try{
				--Temp_top;
				FileWriter fw=new FileWriter(new File(Generating.filename+".txt"),true);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(DATA[Temp_top]+"\n");
			bw.close();
			   }catch(Exception z){
			new Generating().Error();}
			}
	}
	top=-1;
 }
}

class Numbers
{
	Numbers()
	{
		//Creating Frame
		JFrame Temp=new JFrame("Select Values");
		Temp.setSize(450,250);
		Temp.setVisible(true);
		Temp.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
		Temp.setLayout(null);
		Temp.setResizable(false);

		//Heading:
		JLabel Heading=new JLabel("Choose The Keys Yourself:");
		Heading.setBounds(20,20,500,40);
		Temp.add(Heading);

		//ADDING CHECKBOXES:
		JCheckBox Custom_CB[]=new JCheckBox[10];
		for(int i=0;i<10;i++)
		{
			String s=""+i;
			Custom_CB[i]=new JCheckBox(s);
			Temp.add(Custom_CB[i]);
			Custom_CB[i].setBounds(20+(i*40),60,40,40);
		}

		//Adding Optional Checkbox:
		JCheckBox Extra=new JCheckBox("May Contain Other Also");
		Extra.setBounds(130,120,250,40);
		Temp.add(Extra);
		
		//Adding Submit Button:
		JButton Submit=new JButton("Submit");
		Temp.add(Submit);
		Submit.setBounds(150,160,80,30);

		//After Submiting:
		Submit.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
			App.For_Nums="";
			for(int i=0;i<10;i++)
			  if(Custom_CB[i].isSelected())
	   		     App.For_Nums+=Custom_CB[i].getText();

			if(Extra.isSelected())
			{
			 App.For_Nums="";
			 for(int i=0;i<10;i++)
			  App.For_Nums+=i;
			}
		if(App.For_Nums.length()==0)
		JOptionPane.showMessageDialog(Temp,"You Have Selected Nothing!","Alert",JOptionPane.WARNING_MESSAGE);
		else{
		  	JOptionPane.showMessageDialog(Temp,App.For_Nums);
		  	Temp.setVisible(false);
		  	Temp.dispose();
		  }
		}
		});
	}
}

class Smalla_z
{
	Smalla_z()
	{
		//Creating frame
		JFrame Temp=new JFrame("Select Values");
		Temp.setSize(350,400);
		Temp.setVisible(true);
		Temp.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
		Temp.setLayout(null);
		Temp.setResizable(false);

		//Creating Heading:
		JLabel Heading=new JLabel("Choose The Keys Yourself:");
		Heading.setBounds(20,20,500,40);
		Temp.add(Heading);

		//Adding Custom Checkboxes:
		JCheckBox Custom_CB[]=new JCheckBox[26];
		for(int i=0,j=-1,k=-1;i<26;i++)
		{
			String s=""+(char)('a'+i);
			Custom_CB[i]=new JCheckBox(s);
			Temp.add(Custom_CB[i]);
			j=i%7;
			if(j==0)
			   k++;
			Custom_CB[i].setBounds(20+(j*40),60+(k*40),40,40);
		}

		//Adding Extras 
		JCheckBox Extra=new JCheckBox("May Contain Other Also");
		Extra.setBounds(85,230,250,40);
		Temp.add(Extra);
		
		//Adding Submit
		JButton Submit=new JButton("Submit");
		Temp.add(Submit);
		Submit.setBounds(120,270,80,30);

		//After Submiting:
		Submit.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
			App.For_a_z="";
			for(int i=0;i<26;i++)
			if(Custom_CB[i].isSelected())
			  App.For_a_z+=Custom_CB[i].getText();
			
			if(Extra.isSelected())
			{
				App.For_a_z="";
				for(int i=0;i<26;i++)
			 	  App.For_a_z+=Custom_CB[i].getText();
			}
		if(App.For_a_z.length()==0){
		JOptionPane.showMessageDialog(Temp,"You Have Selected Nothing!","Alert",JOptionPane.WARNING_MESSAGE);
			}
		else
		  {
		  	JOptionPane.showMessageDialog(Temp,App.For_a_z);
		  	Temp.setVisible(false);
		  	Temp.dispose();
		  }
		}
		});
	}
}
class A_Z
{
	A_Z()
	{
		//Adding Frame
		JFrame Temp=new JFrame("Select Values");
		Temp.setSize(350,400);
		Temp.setVisible(true);
		Temp.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
		Temp.setLayout(null);
		Temp.setResizable(false);

		//Adding Heading:
		JLabel Heading=new JLabel("Choose The Keys Yourself:");
		Heading.setBounds(20,20,500,40);
		Temp.add(Heading);

		//Adding Custom CheckBoxes:
		JCheckBox Custom_CB[]=new JCheckBox[26];
		for(int i=0,j=-1,k=-1;i<26;i++)
		{
			String s=""+(char)('A'+i);
			Custom_CB[i]=new JCheckBox(s);
			Temp.add(Custom_CB[i]);
			j=i%7;
			if(j==0)
			  k++;
			Custom_CB[i].setBounds(20+(j*40),60+(k*40),40,40);
		}

		//Adding Extras:
		JCheckBox Extra=new JCheckBox("May Contain Other Also");
		Extra.setBounds(85,230,250,40);
		Temp.add(Extra);
		
		//Adding Submit:
		JButton Submit=new JButton("Submit");
		Temp.add(Submit);
		Submit.setBounds(120,270,80,30);

		//After Submiting:
		Submit.addActionListener(new ActionListener()
		{
	    	   public void actionPerformed(ActionEvent e)
		   {
			App.For_A_Z="";
			for(int i=0;i<26;i++)
			   if(Custom_CB[i].isSelected())
			      App.For_A_Z+=Custom_CB[i].getText();
			
			if(Extra.isSelected())
			{
				App.For_A_Z="";
				for(int i=0;i<26;i++)
			 	  App.For_A_Z+=Custom_CB[i].getText();
			}
		if(App.For_A_Z.length()==0){
		JOptionPane.showMessageDialog(Temp,"You Have Selected Nothing!","Alert",JOptionPane.WARNING_MESSAGE);
			}
		else
		  {
		  	JOptionPane.showMessageDialog(Temp,App.For_A_Z);
		  	Temp.setVisible(false);
		  	Temp.dispose();
		  }
		}
		});
	}
}


public class App
{
JFrame jf;
JPopupMenu Rfrsh;
JMenuItem Refresh;
JCheckBox Nums,LtrsL,LtrsU,C[];
JLabel L_Heading;
JButton Submit,Custom_Button[];

//DATA:
static int L_O_P=0; //To Set the Length Of PassWord!!
static String For_Nums="";
static String For_a_z="";
static String For_A_Z="";
static String Symbols="";
static Boolean Rpt=false;
static String Fixed="";
static String FinalKey="";
	App()
	{	
	//Creating Frame
	jf=new JFrame("PassWordGenerator");
	jf.setVisible(true);
	jf.setSize(430,530);
	jf.setLayout(null);
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jf.setResizable(false);
	jf.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));

	//CREATING REFRESH BUTTON
	Rfrsh=new JPopupMenu();
	Refresh=new JMenuItem("Refresh");
	Rfrsh.add(Refresh);
	jf.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e)
		{
			Rfrsh.show(jf,e.getX(),e.getY());		
		}
	});
	jf.add(Rfrsh);
	Refresh.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			jf.setVisible(false);
			jf.dispose();
			new App();
		}
	});

	//Adding reset Menu!!
	JMenuBar mb=new JMenuBar();
	JMenu m=new JMenu("Help!");
	JMenuItem mi=new JMenuItem("Reset");
	m.add(mi);
	mb.add(m);
	jf.setJMenuBar(mb);
	mi.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
		//Reseting The Values:
		L_O_P=0;
		For_Nums="";
		For_a_z="";
		For_A_Z="";
		Symbols="";
		Rpt=false;
		Fixed="";
		FinalKey="";

		jf.setVisible(false);
		jf.dispose();
		new App();
		}
	});


	//Creating Heading:  -----> Wordlist..
	JLabel Heading=new JLabel("!!  W o r d L i s t ... !!");
	jf.add(Heading);
	Heading.setBounds(150,10,200,50);
	

	//Adding Length TextArea:
	JLabel L1=new JLabel("Length Of PassWord:");
	JSpinner S=new JSpinner(new SpinnerNumberModel(1,1,16,1));
	L1.setBounds(30,60,150,30);
	S.setBounds(170,60,50,30);
	jf.add(L1);
	jf.add(S);


	//Checking The Types: ---->Adding Checkboxes num ,a_z ,A_Z
	L_Heading=new JLabel("PassWord May Contain: ");
	jf.add(L_Heading);
	L_Heading.setBounds(30,100,150,30);

	Nums=new JCheckBox("Numbers ( 0-9 )");
	LtrsL=new JCheckBox("Small Letters ( a-z )");
	LtrsU=new JCheckBox("Capital Letters ( A-Z )");
	Nums.setBounds(30,130,200,30);jf.add(Nums);
	LtrsL.setBounds(30,160,200,30);jf.add(LtrsL);
	LtrsU.setBounds(30,190,200,30);jf.add(LtrsU);
	JLabel List=new JLabel("Symbols:");
	List.setBounds(30,220,100,30);jf.add(List);

	//ADDING SYMBOLS:
	C=new JCheckBox[11];
	C[0]=new JCheckBox("~");C[0].setBounds(30,240,40,40);jf.add(C[0]);
	C[1]=new JCheckBox("@");C[1].setBounds(70,240,40,40);jf.add(C[1]);
	C[2]=new JCheckBox("#");C[2].setBounds(110,240,40,40);jf.add(C[2]);
	C[3]=new JCheckBox("$");C[3].setBounds(150,240,40,40);jf.add(C[3]);
	C[4]=new JCheckBox("%");C[4].setBounds(190,240,40,40);jf.add(C[4]);
	C[5]=new JCheckBox("&");C[5].setBounds(230,240,40,40);jf.add(C[5]);
	C[6]=new JCheckBox("*");C[6].setBounds(270,240,40,40);jf.add(C[6]);
	C[7]=new JCheckBox("\\");C[7].setBounds(310,240,40,40);jf.add(C[7]);
	C[8]=new JCheckBox("/");C[8].setBounds(350,240,40,40);jf.add(C[8]);
	C[9]=new JCheckBox("?");C[9].setBounds(30,280,40,40);jf.add(C[9]);
	C[10]=new JCheckBox("^");C[10].setBounds(70,280,40,40);jf.add(C[10]);


	//SPECIFICATIONS FROM USER!!
	Custom_Button=new JButton[3];
	for(int i=0;i<3;i++)
	{
		Custom_Button[i]=new JButton("Custom");
		Custom_Button[i].setBounds(250,130+(i*30),100,25);
		jf.add(Custom_Button[i]);
	}

	//Adding Function For Custom_Buttons:
	Custom_Button[0].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{new Numbers();}});
	Custom_Button[1].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{new Smalla_z();}});
	Custom_Button[2].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{new A_Z();}});


	//Selecting Options For Repeatation:
	JLabel Repeat=new JLabel("Repeatation is allowed?? ");
	Repeat.setBounds(30,320,200,30);
	jf.add(Repeat);
	JRadioButton R1=new JRadioButton("Allowed");
	JRadioButton R2=new JRadioButton("Not Allowed");
	R1.setBounds(60,350,150,30);R2.setBounds(60,370,150,30);
	ButtonGroup bg=new ButtonGroup();
	bg.add(R1);bg.add(R2);
	jf.add(R1);jf.add(R2);


	//Adding TextArea For Getting The Values That User may Have Doubt:
	JLabel Doubt=new JLabel("Any Key User Knows:");
	Doubt.setBounds(280,310,150,50);
	jf.add(Doubt);
	JTextField Board=new JTextField();
	Board.setBounds(300,350,70,30);
	jf.add(Board);

	//Submitting Button:
	Submit=new JButton("Start Generating");
	Submit.setBounds(150,410,150,30);
	jf.add(Submit);



	//========================= FUNCTIONALITY ========================//
	//After Submitting:
	Submit.addActionListener(new ActionListener()
	{
	   public void actionPerformed(ActionEvent e)
	    {
		//getting length
		{
			String ST=""+S.getValue();
			L_O_P=Integer.parseInt(ST);
		}
		
		//Check if Num_CheckBox is selected or not!!
		if(Nums.isSelected())
		{
			For_Nums="";
			for(int i=0;i<10;i++)
				For_Nums+=i;
		}

		//Check if LtrsL_CheckBox is selected or not!!
		if(LtrsL.isSelected())
		{
			For_a_z="";
			for(int i=0;i<26;i++)
				For_a_z+=(char)('a'+i);
		}

		//Check if LtrsU_CheckBox is selected or not!!
		if(LtrsU.isSelected())
		{
			For_A_Z="";
			for(int i=0;i<26;i++)
				For_A_Z+=(char)('A'+i);
		}

		//Getting Symbols
		{
			Symbols="";
			for(int i=0;i<11;i++)
			if(C[i].isSelected())
				Symbols+=C[i].getText();
		}

		//Checking for Repeatation	
		if(R1.isSelected())
	  	 Rpt=true; 
		else
		 Rpt=false;

		//Reading the TextArea: ---------->These must be in PassWord:
		Fixed=Board.getText();

		//COMBINING ALL VALUES:
		FinalKey=For_Nums+For_a_z+For_A_Z+Symbols+Fixed;


	//=================  WORKING PART  =========================//
	if((L_O_P==Fixed.length()&&(FinalKey.length()-Fixed.length())==0)||(L_O_P>Fixed.length()&&FinalKey.length()!=0))
	{
		int a=JOptionPane.showConfirmDialog(jf,"Numbers: "+For_Nums+"\nSmall Letters: "+For_a_z+"\nCapital Letters: "+For_A_Z+"\nSymbols: "+Symbols+"\nFixed Keys: "+Fixed+"\nDo You Want To Continue?");
		
		//Removing Repeatation because of Fixed Values:
		if(Fixed.length()!=0)
		{
		 String Temp="";
		 Boolean flag=true;
		 for(int i=0;i<FinalKey.length();i++)
		  {
			flag=true;
			for(int j=0;j<Temp.length();j++)
				if(FinalKey.charAt(i)==Temp.charAt(j))
					{ flag=false; break; }
			if(flag)
			Temp+=FinalKey.charAt(i);
		  }
		  FinalKey=Temp;
		}

		//CALCULATING NUMBERS OF POSSIBLE WORDS:
		{
		 //IF THERE IS REPEATATION:
		 if(Rpt)
		 {
			double q=1;
			
		//CALCULATION MISTAKE!!!!
		{
		for(int i=0;i<L_O_P;i++)
			q*=FinalKey.length();
		
		//->Subtracting Extras:(Which doesnot contain Fixed values)
		 double t=0;
		if(Fixed.length()!=0){
		t=1;
		 for(int i=0;i<L_O_P;i++)
			t*=(FinalKey.length()-Fixed.length());		
		}


		JOptionPane.showMessageDialog(jf,(q-t)+" Possible Words Are Possible\nWith REPEATATION","Alert",JOptionPane.WARNING_MESSAGE);
		
		Generating.filename=JOptionPane.showInputDialog(jf,"Enter The File Name");
		}
		jf.setVisible(false);
		jf.dispose();

		Generating.Total_Word=q;
		new Generating(true);
		 }
		 //IF NO REPEATATION:
		else
		 {	
		if(FinalKey.length()==Fixed.length())
			L_O_P=Fixed.length();

		double q=1;
		// CALCULATION MISTAKE!!!!!!!!!!! 
		{
		int temp=FinalKey.length();
		double t=0;
		for(int i=0;i<L_O_P;i++)
			q*=(temp--);

		if(FinalKey.length()-Fixed.length()>=L_O_P&&Fixed.length()!=0)
		{
		t=1;
		double TeMp=FinalKey.length()-Fixed.length();
		for(int i=0;i<L_O_P;i++)
			t*=TeMp--;
		}

		JOptionPane.showMessageDialog(jf,(q-t)+" Possible Words Are Possible\nWithout REPEATATION","Alert",JOptionPane.WARNING_MESSAGE);	
		
		Generating.filename=JOptionPane.showInputDialog(jf,"Enter The File Name");
		}
		jf.setVisible(false);
		jf.dispose();
		
		q=1;
		for(int i=0;i<L_O_P;i++)
			q*=FinalKey.length();

		Generating.Total_Word=q;
		new Generating(false);
		 }
		}
	}else if(FinalKey.length()==0)
	  {
		JOptionPane.showMessageDialog(jf,"You Have Selected Nothing!","Alert",JOptionPane.WARNING_MESSAGE);
	  }else
	       {
		JOptionPane.showMessageDialog(jf,"Length of PassWord Is Less !!","Alert",JOptionPane.WARNING_MESSAGE);
		}
	    }
	});
 }

public double fact(int data)
 {
    double d=1;
    for(int i=data;i>0;i--)
	d*=i*(1.0);
    return d;
 }
public double power(int base,int exp)
 {
	double d=1;
	for(int i=0;i<exp;i++)
	    d*=base*1.0;
        return d;
 }
public double Comb(double Total,double Choosen)
 {
	double d=1;
	double Temp=Total-Choosen;
	if(Temp>Choosen)
	{
		d*=Permu(Total,Temp);
		d/=fact((int)Choosen);
	}
	else
	{
		d*=Permu(Total,Choosen);
		d/=fact((int)Temp);
	}	
	return d;
 }
public double Permu(double Total,double Choosen)
 {
	double d=1;
	for(double i=Total;i>Choosen&&i!=0;i--)
		d*=i;
	return d;
 }

public static void main(String...args)
 {
	App obj=new App();
 }
}