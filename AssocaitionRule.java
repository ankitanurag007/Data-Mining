public static void main(String args[]) {
		try {
			AprioriGUI frame = new AprioriGUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AprioriGUI() {
		super("Data Mining");
		getContentPane().setLayout(null);
		setBounds(100, 100, 536, 408);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JLabel open = new JLabel();
		open.setText("Open a file");
		open.setBounds(10, 11, 140, 23);
		getContentPane().add(open);
		
		final JButton button_open = new JButton();
		button_open.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String fs = "C:\\Users\\MOHIT\\Desktop";
				JFileChooser jFileChooser = new JFileChooser(fs);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv", "csv");
				jFileChooser.setFileFilter(filter);
				int r = jFileChooser.showOpenDialog(AprioriGUI.this);			
				if(r == JFileChooser.APPROVE_OPTION) {
				File f = jFileChooser.getSelectedFile();
				try {
					String source, dest;
					source = f.getAbsolutePath();
					FileReader txt = new FileReader(source); //source
 					BufferedReader br= new BufferedReader(txt);
 					int count=0;
 					while (br.readLine() != null)
    					 count++;
 					br.close();
 					txt = new FileReader(source);
					br= new BufferedReader(txt);// open again
 					String a[][] = new String[count][]; //dynamic creation
 					String str1;
 					int i=0;
 					while ((str1=br.readLine()) != null){
 						a[i] = str1.split(","); 						
     					i++;     					
 					} //end of while
 					total=i;
 					String str = "transaction	itemset" + "\n";
 					int x, y;
 					int trans_count = 1; 
 					String output ="";						 					
 					for (x=0;x<a.length;x++){
 						output += "T" + trans_count + "\t";
 						for (y=0;y<a[x].length;y++){
 							output += a[x][y] + " ";
 						}	
 						if (trans_count<=x+1){
 						output +="\n";
 						trans_count++; 						
 						}
  					}
 					textArea.setText(str + output); 					
 					info = a;									
				} 
				catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File doesn't exist \ntake database.txt file to the same folder");
				}
			}
			}
		});
		button_open.setText("Open...");
		button_open.setBounds(140, 11, 80, 23);
		getContentPane().add(button_open);

		final JLabel label = new JLabel();//min_support
		label.setText("minimal support");
		label.setBounds(10, 40, 140, 23);
		getContentPane().add(label);

		limintCon = new JTextField();
		limintCon.setBounds(140, 69, 90, 21);
		getContentPane().add(limintCon);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					String str = support.getText();
					int val = Integer.parseInt(str);
					String str1 = "Frequent itemsets generated as below(minimal support is " + str + ")" + "\n";		
					ht1.clear();
					ht2.clear();
					ht3.clear();
					textArea_frequency.setText(str1 + createL3());										
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(null, "key minimal support,e.g:1 ");
				}
			}
		});
		button.setText("Generate freq-itemset");
		button.setBounds(285, 40, 180, 23);		
		getContentPane().add(button);//min_support		

		final JLabel label_1 = new JLabel();//confidence
		label_1.setText("minimal confidence");
		label_1.setBounds(10, 69, 140, 15);
		getContentPane().add(label_1);

		support = new JTextField();
		support.setBounds(140, 41, 90, 21);
		getContentPane().add(support);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					String str = limintCon.getText();
					String str2 = "Association rule generated as below(minimal confidence is " + str + ")" + "\n";
					textArea_relating.setText(str2 + createTransRule());
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(null, "key minimal confidence,e.g:0.5 ");
				}
			}
		});
		button_1.setText("Generate association rule");
		button_1.setBounds(285, 65, 180, 23);
		getContentPane().add(button_1);//confidence	

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBounds(10, 100, 508, 289);
		getContentPane().add(panel);

		final JTabbedPane tabbedPane = new JTabbedPane();
		panel.add(tabbedPane, BorderLayout.CENTER);

		final JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout());
		tabbedPane.addTab("transactional DB", null, panel_3, null);

		textArea = new JTextArea();		
		textArea.enable(false);
		panel_3.add(textArea, BorderLayout.CENTER);

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());
		tabbedPane.addTab("frequent itemset", null, panel_1, null);

		final JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		textArea_frequency = new JTextArea();
		scrollPane.setViewportView(textArea_frequency);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		tabbedPane.addTab("association rule", null, panel_2, null);

		final JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);

		textArea_relating = new JTextArea();
		scrollPane_1.setViewportView(textArea_relating);
	}	
	
	public String print(Hashtable ht) {
		String str = "";
		Set s = ht.keySet();
		for (Iterator i = s.iterator(); i.hasNext();) {
			Object next = i.next();
			
			str = str + next + "----->";
			str = str + ht.get(next);
			str = str + "\n";
		}
		return str;
	}	
	
	public void removeNotSupportKey(Hashtable ht) {
		String str = support.getText();

		Double percentage = Double.parseDouble(str);
		Double j = (percentage/100)*total;
		
		Set s = ht.keySet();
		for (Iterator i = s.iterator(); i.hasNext();) {

			if (Integer.parseInt((String) ht.get(i.next())) < j) {
				i.remove();
			}
		}
	}
	
	public String createL1() {
		String str = "";
		
		for (int i = 0; i < info.length; i++) {
			for (int j = 0; j < info[i].length; j++) {
				if (!ht1.containsKey(info[i][j])) {
					ht1.put(info[i][j], "1");
				} else {
					ht1.put(info[i][j], Integer.parseInt((String) ht1.get(info[i][j]))+ 1 + "");
				}
			}
		}
		removeNotSupportKey(ht1);
		str = "frequent 1-itemset as below:" + "\n";
		str = str + print(ht1) + "\n";
		str = str + "------------------------" + "\n";
		return str;
	}
	

	public String  createL2() {
		String str = "";
		str = createL1();
		Set s = ht1.keySet();
		for (Iterator iter = s.iterator(); iter.hasNext();) {
			Object o = iter.next();
			for (int i = 0; i < info.length; i++) {
				for (int j = 0; j < info[i].length - 1; j++) {
					if (o.equals(info[i][j])) {
						for (int k = j + 1; k < info[i].length; k++) {
							if (!this.contain(ht2.keySet(), (String) o,info[i][k], null))
							{
								Item item = new Item((String) o, info[i][k]);
								ht2.put(item, "1");
							} 
							else 
							{
								Object key = this.findKey(ht2.keySet(),
										(String) o, info[i][k], null);
								ht2.put(key, Integer.parseInt((String) ht2.get(key)) + 1 + "");
							}
						}
						break;
					}
				}
			}
		}
		removeNotSupportKey(ht2);
		
		str = str + "frequent 2-itemset as below:" + "\n";
		str = str + print(ht2) + "\n";
		str = str + "------------------------" + "\n";
		return str;
	}
	public String createL3() 
	{
		String str = "";
		
		str = createL2();
		Set s = ht2.keySet();
		for (Iterator iter = s.iterator(); iter.hasNext();) 
		{
			Item o = (Item) iter.next();
			for (int i = 0; i < info.length; i++) 
			{
				for (int j = 0; j < info[i].length - 1; j++)
				{
					if (o.getA().equals(info[i][j])
							&& o.getB().equals(info[i][j + 1])
							&& j + 1 < info.length) {
						for (int k = j + 2; k < info[i].length; k++) 
						{
							if (!this.contain(ht3.keySet(), o.getA(), o.getB(),
									info[i][k])) 
							{	
								Item item = new Item(o.getA(), o.getB(),
										info[i][k]);
								ht3.put(item, "1");
							} else {
								Object key = this.findKey(ht3.keySet(), o
										.getA(), o.getB(), info[i][k]);
								ht3.put(key, Integer.parseInt((String) ht3.get(key))+ 1 + "");
							}
						}
						break;
					}
				}
			}
		}
		removeNotSupportKey(ht3);
		str = str + "frequent 3-itemset as below" + "\n";
		str = str + print(ht3) + "\n";
		str = str + "\n";
		return str;
		
		
	}
	
	public Object findKey(Set keyset, String a, String b, String c) {
		if (b == null) {
			for (Iterator i = keyset.iterator(); i.hasNext();) {
				Object n = i.next();
				if (n.equals(a)) {
					return n;
				}
			}
		}
		if (c == null && b != null) {
			for (Iterator i = keyset.iterator(); i.hasNext();) {
				Object n = i.next();
				Item it = (Item) n;
				if (it.getA().equals(a) && it.getB().equals(b)) {
					return n;
				}
			}
		} else if (c != null) {
			for (Iterator i = keyset.iterator(); i.hasNext();) {
				Object n = i.next();
				Item it = (Item) n;
				if (it.getA().equals(a) && it.getB().equals(b)
						&& it.getC().equals(c)) {
					return n;
				}
			}
		}
		return null;
	}
	
	public boolean contain(Set keyset, String a, String b, String c) {
		if (c == null) {
			for (Iterator i = keyset.iterator(); i.hasNext();) {
				Item it = (Item) i.next();
				if (it.getA().equals(a) && it.getB().equals(b)) {
					return true;
				}
			}
		} else if (c != null) {
			for (Iterator i = keyset.iterator(); i.hasNext();) {
				Item it = (Item) i.next();
				if (it.getA().equals(a) && it.getB().equals(b)
						&& it.getC().equals(c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String createTransRule() {
		String result = "";
		String str = limintCon.getText();
		double d = Double.valueOf(str);
		
		Set s = ht3.keySet();
		int subCon = 0;
		for (Iterator i = s.iterator(); i.hasNext();) {
			Item next = (Item) i.next();
			String a = next.getA();
			String b = next.getB();
			String c = next.getC();
			String[] L = new String[] { a, b, c };
			ArrayList subSet = this.getSubSet(L);
			for (Iterator it = subSet.iterator(); it.hasNext();) {
				String[] setN = (String[]) it.next();
				if (setN[0] != null && setN[1] == null) {
					Object key = this.findKey(ht1.keySet(), setN[0], setN[1],
							setN[2]);
					subCon = Integer.parseInt((String) ht1.get(key));
				} else if (setN[0] != null && setN[1] != null
						&& setN[2] == null) {
					Object key = this.findKey(ht2.keySet(), setN[0], setN[1],
							setN[2]);
					subCon = Integer.parseInt((String) ht2.get(key));
				} else if (setN[0] != null && setN[1] != null
						&& setN[2] != null) {
					Object key = this.findKey(ht3.keySet(), setN[0], setN[1],
							setN[2]);
					subCon = Integer.parseInt((String) ht3.get(key));
				}
				float LCon = (float) Double.parseDouble((String) ht3.get(next));
				if ((LCon / subCon) - d >= 0 && setN[0] != null) {
					String M[] = this.getMinusCollect(setN, L);
					
					
					if (M[0] == "" && M[1] == "" && M[2] == "") {
						continue;
					}
					;
					
					
					if (setN[0] == null) {
						setN[0] = "";
					}
					;
					if (setN[1] == null) {
						setN[1] = "";
					}
					;
					if (setN[2] == null) {
						setN[2] = "";
					}
					;
					
					result = result + setN[0] + setN[1] + setN[2] + "==>" + M[0]+ M[1] + M[2]
					         + "------ : " + LCon/ subCon + "\n"; //support(L)  »  Psupport(a)
				}
			}
		}
		
		return result;
	}

	public String[] getMinusCollect(String[] a, String[] L) {
		String[] LL = L.clone();
		for (int i = 0; i < L.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (L[i].equals(a[j])) {
					LL[i] = "";
					break;
				}
			}
		}
		return LL;
	}

	public ArrayList getSubSet(String setN[]) {
		int length = setN.length;
		int i;
		int a = 1 << length;
		String subSet[][] = new String[1 << length][setN.length];
		for (i = 0; i < a; i++) {
			for (int j = 0; j < length; j++) {
				int b = 1 << j;
				if ((i & b) != 0) {
					subSet[i][j] = setN[j];
				}
			}
		}
		ArrayList aL = new ArrayList();
		for (int n = 0; n < subSet.length; n++) {
			String sub[] = new String[subSet[n].length];
			int q = 0;
			for (int m = 0; m < subSet[n].length; m++) {
				if (subSet[n][m] != null) {
					sub[q] = subSet[n][m];
					q++;
				}
			}
			aL.add(sub);
		}
		return aL;
	}

	class Item {
		private String a = "";

		private String b = "";

		private String c = "";

		Item() {
		}

		Item(String a) {
			this.a = a;
		}

		Item(String a, String b) {
			this.a = a;
			this.b = b;
		}

		Item(String a, String b, String c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public String getA() {
			return a;
		}

		public String getB() {
			return b;
		}

		public String getC() {
			return c;
		}

		public String toString() {
			return a + b + c;
		}
	}
}
