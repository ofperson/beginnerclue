package form;

import data.ClueMonster;
import data.ClueSettings;
import data.Vars;
import org.dreambot.api.Client;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.items.Item;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class ClueFrame extends JFrame {

	private JPanel contentPane;
	private JTextField lastProfileField;
	private JTextField weaponField;
	private JTextField shieldField;
	private JTextField legsField;
	private JTextField chestField;
	private JTextField helmetField;
	private JTextField capeField;
	private JTextField gloveField;
	private JTextField bootsField;
	private JTextField amuletField;
	private JTextField ringField;
	private JTextField amountRestock;
	private JComboBox<String> monsterBox;
	private JComboBox<String> locationBox;
	private JCheckBox enableRestockCheck;
	private JCheckBox enableEmoteBuyCheck;
	private JButton saveProfile;
	private JButton loadProfile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClueFrame frame = new ClueFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClueFrame() {
		setResizable(false);
		setTitle("Beginner Clue Solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Start", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Beginner Clue Solver");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel.setBounds(10, 11, 273, 55);
		panel.add(lblNewLabel);

		JEditorPane dtrpnCompletesBeginnerClue = new JEditorPane();
		dtrpnCompletesBeginnerClue.setEditable(false);
		dtrpnCompletesBeginnerClue.setText("Completes beginner clue scrolls in F2P.\r\n\r\nKills selected monster in selected location to loot \r\nand complete clues.\r\n\r\nSupports restocking of Charlie the Tramp and \r\nemote items!\r\n\r\nWill collect strange device and spade for hot and \r\ncold clues!\r\n\r\nPlease fill out configuration in each tab before \r\nrunning!");
		dtrpnCompletesBeginnerClue.setBackground(UIManager.getColor("Button.background"));
		dtrpnCompletesBeginnerClue.setBounds(10, 63, 273, 298);
		dtrpnCompletesBeginnerClue.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(dtrpnCompletesBeginnerClue);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.info("Script started via GUI!");

				// set monster
				Vars.get().setMonster(ClueMonster.fromName(monsterBox.getSelectedItem().toString()));

				// set equipment
				Set<String> equip = new HashSet<>();
				if (!weaponField.getText().equals("")) equip.add(weaponField.getText());
				if (!shieldField.getText().equals("")) equip.add(shieldField.getText());
				if (!chestField.getText().equals("")) equip.add(chestField.getText());
				if (!legsField.getText().equals("")) equip.add(legsField.getText());
				if (!helmetField.getText().equals("")) equip.add(helmetField.getText());
				if (!capeField.getText().equals("")) equip.add(capeField.getText());
				if (!gloveField.getText().equals("")) equip.add(gloveField.getText());
				if (!bootsField.getText().equals("")) equip.add(bootsField.getText());
				if (!amuletField.getText().equals("")) equip.add(amuletField.getText());
				if (!ringField.getText().equals("")) equip.add(ringField.getText());
				Vars.get().setEquipment(equip);

				// set restocking charlie
				Vars.get().setRestockCharlie(enableRestockCheck.isSelected());
				if (enableRestockCheck.isSelected()) {
					try {
						int restockAmount = Integer.valueOf(amountRestock.getText());
						Vars.get().setCharlieRestockAmount(restockAmount);

						int pricePlus = 30;//Integer.valueOf(pricePlusRestock.getText());
						Vars.get().setBuyPlusTicks(pricePlus);
					} catch (NumberFormatException e) {
						Logger.error("Error parsing restock amounts.");
						return;
					}
				}

				// set buy emote items + spade
				Vars.get().setBuyEmoteItems(enableEmoteBuyCheck.isSelected());
				if (enableEmoteBuyCheck.isSelected()) {
					try {
						int pricePlus = 30;//Integer.valueOf(pricePlusEmote.getText());
						Vars.get().setBuyEmotePlusTicks(pricePlus);
					} catch (NumberFormatException e) {
						Logger.error("Error parsing restock amounts.");
						return;
					}
				}

				String profile = lastProfileField.getText();
				if (profile.equals("lastRun")) {
					saveProfile.doClick();
				}

				dispose();
			}
		});
		btnNewButton.setBounds(293, 311, 144, 50);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Profile");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(293, 55, 86, 26);
		panel.add(lblNewLabel_1);

		lastProfileField = new JTextField();
		lastProfileField.setText("lastRun");
		lastProfileField.setBounds(293, 79, 144, 20);
		panel.add(lastProfileField);
		lastProfileField.setColumns(10);

		saveProfile = new JButton("Save");
		saveProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String profile = lastProfileField.getText();
				if (profile == null || profile.trim() == "") { // TODO check for invalid chars
					Logger.error("Please enter a valid profile name.");
					return;
				}

				// collect vars
				ClueSettings settings = new ClueSettings();

				// combat
				settings.setMonster(monsterBox.getSelectedItem().toString());
				settings.setLocation(locationBox.getSelectedItem().toString());

				// equipment
				settings.setAmulet(amuletField.getText() != null ? amuletField.getText() : "");
				settings.setBoots(bootsField.getText() != null ? bootsField.getText() : "");
				settings.setWeapon(weaponField.getText() != null ? weaponField.getText() : "");
				settings.setShield(shieldField.getText() != null ? shieldField.getText() : "");
				settings.setChest(chestField.getText() != null ? chestField.getText() : "");
				settings.setLegs(legsField.getText() != null ? legsField.getText() : "");
				settings.setHelmet(helmetField.getText() != null ? helmetField.getText() : "");
				settings.setCape(capeField.getText() != null ? capeField.getText() : "");
				settings.setGlove(gloveField.getText() != null ? gloveField.getText() : "");
				settings.setRing(ringField.getText() != null ? ringField.getText() : "");

				// emote
				settings.setBuyEmoteItems(enableEmoteBuyCheck.isSelected());
				settings.setBuyEmotePlusTicks(30);

				// charlie
				settings.setRestockCharlie(enableRestockCheck.isSelected());
				settings.setCharlieRestockAmount(Integer.valueOf(amountRestock.getText())); // catch this
				settings.setBuyPlusTicks(30);

				// save
				ScriptSettings.save(settings, "BeginnerClues", profile + ".json");

				Logger.info("Saved profile: " + profile);
			}
		});
		saveProfile.setBounds(293, 105, 67, 23);
		panel.add(saveProfile);

		loadProfile = new JButton("Load");
		loadProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String profile = lastProfileField.getText();
				if (profile == null || profile.trim() == "") { // TODO check for invalid chars
					Logger.error("Please enter a valid profile name.");
					return;
				}

				ClueSettings settings = null;
				try {
					settings = ScriptSettings.load(ClueSettings.class, "BeginnerClues", profile + ".json");
				} catch (Exception ex) {
					Logger.error("Error loading profile: " + profile);
					return;
				}

				// set fields
				if (settings != null) {
					// combat
					monsterBox.setSelectedItem(settings.getMonster() != null ? settings.getMonster() : "Goblin");
					locationBox.setSelectedItem(settings.getLocation() != null ? settings.getLocation() : "Lumbridge");

					// equipment
					String weapon = settings.getWeapon();
					weaponField.setText(weapon != null ? weapon : "");

					String shield = settings.getShield();
					shieldField.setText(shield != null ? shield : "");

					String chest = settings.getChest();
					chestField.setText(chest != null ? chest : "");

					String legs = settings.getLegs();
					legsField.setText(legs != null ? legs : "");

					String helmet = settings.getHelmet();
					helmetField.setText(helmet != null ? helmet : "");

					String cape = settings.getCape();
					capeField.setText(cape != null ? cape : "");

					String gloves = settings.getGlove();
					gloveField.setText(gloves != null ? gloves : "");

					String boots = settings.getBoots();
					bootsField.setText(boots != null ? boots : "");

					String amulet = settings.getAmulet();
					amuletField.setText(amulet != null ? amulet : "");

					String ring = settings.getRing();
					ringField.setText(ring != null ? ring : "");

					// emote
					enableEmoteBuyCheck.setSelected(settings.isBuyEmoteItems());

					// charlie
					enableRestockCheck.setSelected(settings.isRestockCharlie());
					amountRestock.setText(String.valueOf(settings.getCharlieRestockAmount()));
				}

				Logger.info("Loaded profile: " + profile);
			}
		});
		loadProfile.setBounds(370, 105, 67, 23);
		panel.add(loadProfile);

		JSeparator separator = new JSeparator();
		separator.setBounds(293, 139, 144, 2);
		panel.add(separator);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Combat", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Monster", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 11, 208, 350);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 21, 89, 24);
		panel_3.add(lblNewLabel_2);

		monsterBox = new JComboBox<>();
		monsterBox.setModel(new DefaultComboBoxModel(new String[] {"Goblin", "Cow"}));
		monsterBox.setBounds(10, 45, 188, 22);
		panel_3.add(monsterBox);

		locationBox = new JComboBox<>();
		locationBox.setModel(new DefaultComboBoxModel(new String[] {"Lumbridge"}));
		locationBox.setBounds(10, 102, 188, 22);
		panel_3.add(locationBox);

		JLabel lblNewLabel_2_1 = new JLabel("Location");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2_1.setBounds(10, 78, 89, 24);
		panel_3.add(lblNewLabel_2_1);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Equipment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(228, 11, 209, 350);
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		JButton currEquipButton = new JButton("Get Current Equipment");
		currEquipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Client.isLoggedIn()) {
					String weapon = getEquipmentInSlot(EquipmentSlot.WEAPON);
					weaponField.setText(weapon != null ? weapon : "");

					String shield = getEquipmentInSlot(EquipmentSlot.SHIELD);
					shieldField.setText(shield != null ? shield : "");

					String chest = getEquipmentInSlot(EquipmentSlot.CHEST);
					chestField.setText(chest != null ? chest : "");

					String legs = getEquipmentInSlot(EquipmentSlot.LEGS);
					legsField.setText(legs != null ? legs : "");

					String helmet = getEquipmentInSlot(EquipmentSlot.HAT);
					helmetField.setText(helmet != null ? helmet : "");

					String cape = getEquipmentInSlot(EquipmentSlot.CAPE);
					capeField.setText(cape != null ? cape : "");

					String gloves = getEquipmentInSlot(EquipmentSlot.HANDS);
					gloveField.setText(gloves != null ? gloves : "");

					String boots = getEquipmentInSlot(EquipmentSlot.FEET);
					bootsField.setText(boots != null ? boots : "");

					String amulet = getEquipmentInSlot(EquipmentSlot.AMULET);
					amuletField.setText(amulet != null ? amulet : "");

					String ring = getEquipmentInSlot(EquipmentSlot.RING);
					ringField.setText(ring != null ? ring : "");
				} else {
					Logger.warn("Please login before attempting to grab current equipment.");
				}
			}
		});
		currEquipButton.setBounds(10, 316, 189, 23);
		panel_4.add(currEquipButton);

		JLabel lblNewLabel_3 = new JLabel("Weapon");
		lblNewLabel_3.setBounds(10, 21, 46, 14);
		panel_4.add(lblNewLabel_3);

		weaponField = new JTextField();
		weaponField.setBounds(66, 18, 133, 20);
		panel_4.add(weaponField);
		weaponField.setColumns(10);

		JLabel lblNewLabel_3_1 = new JLabel("Shield");
		lblNewLabel_3_1.setBounds(10, 49, 46, 14);
		panel_4.add(lblNewLabel_3_1);

		shieldField = new JTextField();
		shieldField.setColumns(10);
		shieldField.setBounds(66, 46, 133, 20);
		panel_4.add(shieldField);

		JLabel lblNewLabel_3_2 = new JLabel("Chest");
		lblNewLabel_3_2.setBounds(10, 76, 46, 14);
		panel_4.add(lblNewLabel_3_2);

		JLabel lblNewLabel_3_1_1 = new JLabel("Legs");
		lblNewLabel_3_1_1.setBounds(10, 104, 46, 14);
		panel_4.add(lblNewLabel_3_1_1);

		legsField = new JTextField();
		legsField.setColumns(10);
		legsField.setBounds(66, 101, 133, 20);
		panel_4.add(legsField);

		chestField = new JTextField();
		chestField.setColumns(10);
		chestField.setBounds(66, 73, 133, 20);
		panel_4.add(chestField);

		JLabel lblNewLabel_3_3 = new JLabel("Helmet");
		lblNewLabel_3_3.setBounds(10, 132, 46, 14);
		panel_4.add(lblNewLabel_3_3);

		helmetField = new JTextField();
		helmetField.setColumns(10);
		helmetField.setBounds(66, 129, 133, 20);
		panel_4.add(helmetField);

		capeField = new JTextField();
		capeField.setColumns(10);
		capeField.setBounds(66, 157, 133, 20);
		panel_4.add(capeField);

		JLabel lblNewLabel_3_1_2 = new JLabel("Cape");
		lblNewLabel_3_1_2.setBounds(10, 160, 46, 14);
		panel_4.add(lblNewLabel_3_1_2);

		JLabel lblNewLabel_3_2_1 = new JLabel("Gloves");
		lblNewLabel_3_2_1.setBounds(10, 187, 46, 14);
		panel_4.add(lblNewLabel_3_2_1);

		gloveField = new JTextField();
		gloveField.setColumns(10);
		gloveField.setBounds(66, 184, 133, 20);
		panel_4.add(gloveField);

		JLabel lblNewLabel_3_1_1_1 = new JLabel("Boots");
		lblNewLabel_3_1_1_1.setBounds(10, 215, 46, 14);
		panel_4.add(lblNewLabel_3_1_1_1);

		bootsField = new JTextField();
		bootsField.setColumns(10);
		bootsField.setBounds(66, 212, 133, 20);
		panel_4.add(bootsField);

		JLabel lblNewLabel_3_1_1_1_1 = new JLabel("Ring");
		lblNewLabel_3_1_1_1_1.setBounds(10, 271, 46, 14);
		panel_4.add(lblNewLabel_3_1_1_1_1);

		JLabel lblNewLabel_3_2_1_1 = new JLabel("Amulet");
		lblNewLabel_3_2_1_1.setBounds(10, 243, 46, 14);
		panel_4.add(lblNewLabel_3_2_1_1);

		amuletField = new JTextField();
		amuletField.setColumns(10);
		amuletField.setBounds(66, 240, 133, 20);
		panel_4.add(amuletField);

		ringField = new JTextField();
		ringField.setColumns(10);
		ringField.setBounds(66, 268, 133, 20);
		panel_4.add(ringField);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Restock", null, panel_2, null);
		panel_2.setLayout(null);

		JTextPane txtpnRestocksItemsAt = new JTextPane();
		txtpnRestocksItemsAt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnRestocksItemsAt.setText("Restocks items at Grand Exchange.\r\n\r\nItems: Iron ore, Iron dagger, \r\nRaw herring, Raw trout, Trout, Pike, \r\nLeather body, Leather chaps.");
		txtpnRestocksItemsAt.setEditable(false);
		txtpnRestocksItemsAt.setBounds(10, 11, 208, 135);
		txtpnRestocksItemsAt.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(txtpnRestocksItemsAt);

		JTextPane txtpnBuyEmoteItems = new JTextPane();
		txtpnBuyEmoteItems.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnBuyEmoteItems.setText("Buy emote items at Grand Exchange.\r\n\r\nItems: Gold ring, Gold necklace,\r\nChef's hat, Red cape,\r\nBronze axe, Leather boots,\r\nSpade");
		txtpnBuyEmoteItems.setEditable(false);
		txtpnBuyEmoteItems.setBounds(229, 11, 208, 135);
		txtpnBuyEmoteItems.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(txtpnBuyEmoteItems);

		enableRestockCheck = new JCheckBox("Enable Restock");
		enableRestockCheck.setSelected(true);
		enableRestockCheck.setBounds(10, 156, 124, 23);
		panel_2.add(enableRestockCheck);

		JLabel lblNewLabel_4 = new JLabel("Amount of Items to Buy");
		lblNewLabel_4.setBounds(10, 198, 174, 14);
		panel_2.add(lblNewLabel_4);

		amountRestock = new JTextField();
		amountRestock.setText("25");
		amountRestock.setBounds(10, 223, 124, 20);
		panel_2.add(amountRestock);
		amountRestock.setColumns(10);

		enableEmoteBuyCheck = new JCheckBox("Enable Emote Item Buying");
		enableEmoteBuyCheck.setSelected(true);
		enableEmoteBuyCheck.setBounds(229, 156, 174, 23);
		panel_2.add(enableEmoteBuyCheck);

		// load last profile
		loadProfile.doClick();
	}

	private String getEquipmentInSlot(EquipmentSlot slot) {
		Item item = Equipment.getItemInSlot(slot);
		return item != null ? item.getName() : null;
	}
}
