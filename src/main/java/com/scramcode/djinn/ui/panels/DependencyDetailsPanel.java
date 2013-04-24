package com.scramcode.djinn.ui.panels;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.WindowConstants;


import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import com.scramcode.djinn.model.GraphGranularityComboBoxModel;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings({"rawtypes", "unchecked"})
public class DependencyDetailsPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 541893182308632768L;
    private JScrollPane treeScrollPane;
    private JScrollPane listScrollPane;    
	private JList dependencyList;
    private JComboBox granularityComboBox;
    private JLabel granularityLabel;
    private JTree nodeTree;
    private JLabel helpLabel;

    /**
    * Auto-generated main method to display this 
    * JPanel inside a new JFrame.
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new DependencyDetailsPanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public DependencyDetailsPanel() {
        super();
        initGUI();
    }
    
    private void initGUI() {
        try {
            GroupLayout thisLayout = new GroupLayout(this);
            this.setLayout(thisLayout);
            this.setPreferredSize(new java.awt.Dimension(573, 265));
            {
                helpLabel = new JLabel();
                helpLabel.setText("Click on graph edges to show the dependency details");
            }
            {
                treeScrollPane = new JScrollPane();
                {
                    nodeTree = new JTree();                    
                    treeScrollPane.setViewportView(getNodeTree());
                }
            }
            {
                listScrollPane = new JScrollPane();
                {
                    
					ListModel dependencyListModel = new DefaultComboBoxModel(new String[] { "Item One", "Item Two" });
                    dependencyList = new JList();
                    listScrollPane.setViewportView(getDependencyList());
                    dependencyList.setModel(dependencyListModel);
                }
            }
            {
                granularityLabel = new JLabel();
                granularityLabel.setText("Group by");
            }
            {
                ComboBoxModel granularityComboBoxModel = new GraphGranularityComboBoxModel();
                granularityComboBox = new JComboBox();
                granularityComboBox.setModel(granularityComboBoxModel);
            }
            
            thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .add(thisLayout.createParallelGroup()
                    .add(thisLayout.createSequentialGroup()
                        .add(helpLabel, 0, 744, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.RELATED))
                    .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
                        .add(treeScrollPane, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(thisLayout.createParallelGroup()
                            .add(GroupLayout.LEADING, listScrollPane, 0, 440, Short.MAX_VALUE)
                            .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
                                .add(granularityLabel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(granularityComboBox, 0, 359, Short.MAX_VALUE)))))
                .add(7));
            thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .add(helpLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(thisLayout.createParallelGroup()
                    .add(GroupLayout.LEADING, treeScrollPane, 0, 221, Short.MAX_VALUE)
                    .add(GroupLayout.LEADING, thisLayout.createSequentialGroup()
                        .add(10)
                        .add(thisLayout.createParallelGroup()
                            .add(GroupLayout.BASELINE, granularityComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .add(GroupLayout.BASELINE, granularityLabel))
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(listScrollPane, 0, 180, Short.MAX_VALUE)))
                .addContainerGap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public JTree getNodeTree() {
        return nodeTree;
    }
    
    public JComboBox getGranularityComboBox() {
        return granularityComboBox;
    }
    
    public JList getDependencyList() {
        return dependencyList;
    }

}
