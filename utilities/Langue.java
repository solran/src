package utilities;
import graphic.ImageBox;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import core.Main;
import core.Stimulus;
import core.Task;



public class Langue extends HashMap {
	
	public static HashMap<String, Object> expression = new HashMap<String, Object> ();
	public static HashMap<String, String[][]> stims = new HashMap<String, String[][]> ();

	
	public static void setMenuLangue (String langue)
	{
		//Menu
		if (langue == "francais")
		{			
			expression.put("title", "Programme d'entraînement cognitif");
			expression.put("nBackKeyLabel", "Deux mains");
			
			
			if(! Main.isApplet){
				Utilities.iniHashMap(expression, "tasksNames", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"ASTRES", "LETTRES", "LETTRES GRECQUES", "QUANTITÉS", "PHILLIPS", "SON GAUCHE/DROITE", "FRÉQUENCES"});				
				Utilities.iniHashMap(expression, "otherTasksNames", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"FLÈCHES", "COULEURS", "FORMES", "STYLES", "f", "GO/STOP", "VOYELLES"});
			
				Utilities.iniHashMap(expression, "radioVersion", new String[]{"0", "1", "2", "3" , "4", "5", "6", "7"}, new String[]{"<html><font size = 4>Version de la tâche</font></html>", "1: " + Langue.translate(new String[] {"tasksNames", "0"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "0"}),  
						"2: " + Langue.translate(new String[] {"tasksNames", "1"}) + "  / " + Langue.translate(new String[] {"otherTasksNames", "1"}), 
						"3: " + Langue.translate(new String[] {"tasksNames", "2"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "2"}), 
						"4: " + Langue.translate(new String[] {"tasksNames", "3"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "3"}), 
						"5: " + Langue.translate(new String[] {"tasksNames", "4"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "4"}),
						"6: " + Langue.translate(new String[] {"tasksNames", "5"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "5"}),
						"7: " + Langue.translate(new String[] {"tasksNames", "6"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "6"}),
						"8: " + Langue.translate(new String[] {"tasksNames", "7"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "7"})
				}); 
			
			}
			
			Utilities.iniHashMap(expression, "input", new String[]{"0", "1"}, new String[]{"Ma configuration (optionnelle): ", "Parcourir/Sélectionner..."});

			expression.put("sujetID", "<html><p align=\"center\"><font size = 4>Numéro (#) de <br />participant</font></p></html>");
			
			Utilities.iniHashMap(expression, "radioDuree", new String[]{"0", "1", "2", "3" , "4", "5", "6"}, new String[]{"<html><font size = 4><p align=\"center\"><br /><br /># de blocs </font></html>", "0", "2", "4", "6", "8", "10"});
			
			Utilities.iniHashMap(expression, "radioQteEssai", new String[]{"0", "1", "2", "3"}, new String[]{"<html><font size = 4><p align=\"center\"> # d'essais</font></p></html>", "#-SP", "#-SM", "#-DM"});
			
			Utilities.iniHashMap(expression, "radioType", new String[]{"0", "1", "2"}, new String[]{"<html><font size = 4>Type de session</font></html>", "Évaluation",  "Entrainement"});
			
			Utilities.iniHashMap(expression, "radioLangue", new String[]{"0", "1", "2"}, new String[]{"<html><font size = 4>Langue du participant</font></html>", 
					"Français", "Anglais" });
			
			
			
			Utilities.iniHashMap(expression, "radioQte", new String[]{"0", "1", "2", "3" }, new String[]{"<html><font size = 4>Nombre de possiblités par main</font></html>", 
					"Débutant : 2 par mains", "Intermédiaire : 3 par mains", "Expert : 4 par mains"});
			
			Utilities.iniHashMap(expression, "radioNBack", new String[]{"0", "1", "2", "3", "4",  "boxLabel", "box1", "box2", "box3" }, new String[]{"<html><font size = 4>Réponse à rebours (n-back)</font></html>", "Débutant : 0 rebours",
					"Intermédiaire : 1 rebours","Avancé : 2 rebours", "Expert : 3 rebours", "Type de n-back", "Aucun N-back", "Matching", "Retrieval"});
			
			Utilities.iniHashMap(expression, "radioisDT", new String[]{"info", "0", "1", "2"}, new String[]{"<html><p align=\"center\"><font size = 4># of blocs</font></p></html>", 
					"<html>Avec simple pur</html>", "<html>Avec simple mixte</html>", "<html>Avec double mixte<br/></html>" });
	
			Utilities.iniHashMap(expression, "radioTimeUnlock", new String[]{"0", "1", "tooltip"}, new String[]{"<html><p align=\"center\"><font size = 4>Paramètres de temps</font></p></html>", 
					"Variable (sans limite)*", "<html><p align=\"center\">En mode \"v\", les essais durent aussi longtemps que le participant prend de temps pour y répondre.<br /> Le temps d'affichage des stimuli et le temps de réponse maximal est automatiquement \"Variable\".<br /> Seul l'intervalle inter-stimuli peut être modifier dans ce mode.</html></p>"});
			
			Utilities.iniHashMap(expression, "radioISI", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"<html><p align=\"center\"><font size = 3>Intervalle inter-stimuli</font></p></html>", 
					"500ms", "1000ms", "1500ms", "2000ms", "2500ms", "3000ms" });
			
			Utilities.iniHashMap(expression, "radioMixed", new String[]{"0", "1"}, new String[]{"<html><p align=\"center\"><font size = 4>SM et DM <br /> mélangé<br /><br /></font></p></html>", "<html>% de SM<br /><br /></html>" });
			
			Utilities.iniHashMap(expression, "radioStimT", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, new String[]{"<html><p align=\"center\"><font size = 3>Stimuli On-screen Display</font></p></html>", 
					"Variable", "200ms", "500ms", "1000ms", "1500ms", "2000ms", "2400ms", "3000ms", "3500ms", "4000ms" });
			
			Utilities.iniHashMap(expression, "radioAnswerT", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}, new String[]{"<html><p align=\"center\"><font size = 3>Temps suppl. pour répondre</font></p></html>", 
					"aucun","+500ms", "+1000ms", "+1500ms", "+2000ms", "+2500ms", "+3000ms", "+3500ms", "+4000ms" });
			
			Utilities.iniHashMap(expression, "radioisPrio", new String[]{"0", "1", "2"}, new String[]{"<html><p align=\"center\"><font size = 4>Priorisation des tâches</font></p></html>", 
					"Sans priorité", "Avec priorité" });
			
			Utilities.iniHashMap(expression, "radioFormat", new String[]{"0", "1", "2", "3"}, new String[]{"<html><font size = 4>Modalité de réponse</font></html>", "Clavier standard", "Sans pavé numérique",
			"Tablette (Ipad, Android)"});
	
			Utilities.iniHashMap(expression, "radioisIO", new String[]{"0", "1", "2", "3", "4", "box", "box1", "box2", "box3", "box4", "box5", "box6", "tooltip"}, new String[]{"<html><font size = 4>Imagerie cérébrale</font></html>", "Sans imagerie cérébrale", 
			"Optique (IO)", "Résonnance (IRM)", "EEG", "<html><p align=\"center\"><font size = 3>Durée de chaque pause*</font></p></html>", "aucun", "15 sec" , "30 sec" , "45 sec" , "1:00 min" , "1:15 min", "Les pauses sont accessibles en imagerie seulement. Les pauses précèdent chaqu'un des blocs"});
			
			Utilities.iniHashMap(expression, "output", new String[]{"0", "1"}, new String[]{"Indiquer une sortie si vous désirer enregistrer vos préférences: ", "Parcourir/Enregistrer..."});
			
			Utilities.iniHashMap(expression, "execute", new String[]{"instruction", "button", "waiting", "warning"}, new String[]{"Instruction: Choississez les caractéristiques de votre tâche puis cliquez sur Débuter", "Débuter", "Veuillez patientez...", "Désolé, \"%idSujet%\" n'est pas un numéro de participant valide. Veuillez recommencer S.V.P."});		
		}
		else if (langue == "english")
		{
			expression.put("windows", "Cognitive training program");

			expression.put("title", "Cognitive training program");
			expression.put("nBackKeyLabel", "Both hands");
			
			
			
			expression.put("sujetID", "<html><p align=\"center\"><font size = 4>Participant<br />ID (#)</font></p></html>");
			
			Utilities.iniHashMap(expression, "input", new String[]{"0", "1"}, new String[]{"My settings (optionnal): ", "Run/Select..."});		
			
			Utilities.iniHashMap(expression, "radioDuree", new String[]{"0", "1", "2", "3" , "4", "5", "6"}, new String[]{"<html><font size = 4><p align=\"center\"><br /><br /># of blocs </font></html>", "0", "2", "4", "6", "8", "10"});
			
			Utilities.iniHashMap(expression, "radioQteEssai", new String[]{"0", "1", "2", "3"}, new String[]{"<html><font size = 4><p align=\"center\"> # of trials</font></p></html>", "#-SP", "#-SM", "#-DM"});
			
			Utilities.iniHashMap(expression, "radioType", new String[]{"0", "1", "2"}, new String[]{"<html><font size = 4>Type of Session</font></html>", "Evaluation",  "Training"});
			
			Utilities.iniHashMap(expression, "radioLangue", new String[]{"0", "1", "2"}, new String[]{"<html><font size = 4>Participant Langage</font></html>", 
					"French", "English" });
			
			Utilities.iniHashMap(expression, "radioVersion", new String[]{"0", "1", "2", "3" , "4", "5"}, new String[]{"<html><font size = 4>Version of the Task</font></html>", "1: Astronomy/Arrows",  "2: Letters/Colors", "3: Numbers/Shapes", "4: Dices/Patterns", 
			"5: Phillips"});
			
			Utilities.iniHashMap(expression, "radioQte", new String[]{"0", "1", "2", "3" }, new String[]{"<html><font size = 4>Task-set per hand</font></html>", 
					"Begginer : 2 per hand", "Intermediaire : 3 per hand", "Expert : 4 per hand"});
			
			Utilities.iniHashMap(expression, "radioNBack", new String[]{"0", "1", "2", "3", "4", "box1", "box2", "box3" }, new String[]{"<html><font size = 4>Working Memory (N-back)</font></html>", "Begginer : 0-back",
					"Intermediaire : 1-back","Advanced : 2-back", "Expert : 3-back", "no n-Back", "Matching", "Retrieval"});
			
			Utilities.iniHashMap(expression, "radioisDT", new String[]{"info", "0", "1", "2"}, new String[]{"<html><p align=\"center\"><font size = 4>Blocs</font></p></html>", 
					"<html>Simple-pur</html>", "<html>Simple-mixed</html>", "<html>Dual-mixed</html>" });
	
			Utilities.iniHashMap(expression, "radioTimeUnlock", new String[]{"0", "1"}, new String[]{"<html><p align=\"center\"><font size = 4>Timing parameter</font></p></html>", 
					"<html>Unlock<br /></html>"});
			
			Utilities.iniHashMap(expression, "radioISI", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"<html><p align=\"center\"><font size = 3>Inter-Stimuli Interval</font></p></html>", 
					"500ms", "1000ms", "1500ms", "2000ms", "2500ms", "3000ms" });
			
			Utilities.iniHashMap(expression, "radioMixed", new String[]{"0", "1"}, new String[]{"<html><p align=\"center\"><font size = 4>SM & DM <br /> mixed<br /><br /></font></p></html>", "<html>% of SM<br /><br /></html>" });
			
			
			Utilities.iniHashMap(expression, "radioStimT", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}, new String[]{"<html><p align=\"center\"><font size = 3>Stimuli On-screen Display</font></p></html>", 
					"Variable", "200ms", "500ms", "1000ms", "1500ms", "2000ms", "2400ms", "3000ms", "3500ms", "4000ms" });
			
			Utilities.iniHashMap(expression, "radioAnswerT", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}, new String[]{"<html><p align=\"center\"><font size = 3>Maximum Time Reaction</font></p></html>", 
					"none", "+500ms", "+1000ms", "+1500ms", "+2000ms", "+2500ms", "+3000ms", "+3500ms", "+4000ms" });
			
			Utilities.iniHashMap(expression, "radioisPrio", new String[]{"0", "1", "2"}, new String[]{"<html><p align=\"center\"><font size = 4>Priority on Task</font></p></html>", 
					"Without priority", "With priority" });
			
			Utilities.iniHashMap(expression, "radioFormat", new String[]{"0", "1", "2", "3"}, new String[]{"<html><font size = 4>Response Modality</font></html>", "Standard keyboard", "Without numpad",
			"Tablet (Ipad, Android)"});
			
			Utilities.iniHashMap(expression, "radioisIO", new String[]{"0", "1", "2", "3", "4", "box", "box1", "box2", "box3", "box4", "box5", "box6", "tooltip"}, new String[]{"<html><font size = 4>Brain Imaging</font></html>", "Without imaging", 
					"NIRS", "IRM", "EEG", "<html><p align=\"center\"><font size = 3>Breaks length*</font></p></html>", "aucun", "15 sec" , "30 sec" , "45 sec" , "1:00 min" , "1:15 min", "Les pauses sont accessibles en imagerie seulement et sont réparties avant chaque bloc."});
					
			
			Utilities.iniHashMap(expression, "output", new String[]{"0", "1"}, new String[]{"Please choose a directory if you want to save your settings: ", "Run/Save..."});
			
			Utilities.iniHashMap(expression, "execute", new String[]{"instruction", "button", "waiting", "warning"}, new String[]{"Instructions : Choose the settings you want for this session and click on the Start button", "Start", "Please wait...", "Sorry, \"%idSujet%\" is not a valid number. Please enter another one and press 'Start' again."});
		}
	}
	
	
	public static void setTaskLangue (String langue)
	{
		if (langue == "francais")
		{					
			
			Utilities.iniHashMap(expression, "links", new String[]{"&", "|"}, new String[]{"ET", "OU"});				

			if(Main.isApplet){
				Utilities.iniHashMap(expression, "tasksNames", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"ASTRES", "LETTRES", "LETTRES GRECQUES", "QUANTITÉS", "PHILLIPS", "SON GAUCHE/DROITE", "FRÉQUENCES"});				
				Utilities.iniHashMap(expression, "otherTasksNames", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"FLÈCHES", "COULEURS", "FORMES", "STYLES","f", "GO/STOP", "VOYELLES"});		
			
				Utilities.iniHashMap(expression, "radioVersion", new String[]{"0", "1", "2", "3" , "4", "5", "6", "7"}, new String[]{"<html><font size = 4>Version de la tâche</font></html>", "1: " + Langue.translate(new String[] {"tasksNames", "0"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "0"}),  
						"2: " + Langue.translate(new String[] {"tasksNames", "1"}) + "  / " + Langue.translate(new String[] {"otherTasksNames", "1"}), 
						"3: " + Langue.translate(new String[] {"tasksNames", "2"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "2"}), 
						"4: " + Langue.translate(new String[] {"tasksNames", "3"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "3"}), 
						"5: " + Langue.translate(new String[] {"tasksNames", "4"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "4"}),
						"6: " + Langue.translate(new String[] {"tasksNames", "5"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "5"}),
						"7: " + Langue.translate(new String[] {"tasksNames", "6"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "6"}),
						"8: " + Langue.translate(new String[] {"tasksNames", "7"}) + " / " + Langue.translate(new String[] {"otherTasksNames", "7"})
				}); 
			}
			
			
			Utilities.iniHashMap(expression, "intro", new String[]{"introCenter", "noIRMFooter", "IRMFooter"}, new String[]{
					"Bonjour!", "Prêt ? Veuillez appuyer sur la barre d'espacement pour commencer le premier bloc.", "<html><p align=\"center\">Veuillez patientez pendant que la tâche se synchronise avec l'appareil d'imagerie.</p></html>"});		
			
			expression.put("introFooter", "<html><p align=\"center\">Appuyez sur la barre d’espacement pour avancer dans le menu.</p></html>");

			
			// GeneralExplanation
			if(! Task.mainTask.isStimsAreSounds())
				expression.put("keyGeneralExplanationHeader", "<html><p align=\"center\">Pour ce bloc, %stim1% vont apparaître au<br /> centre de l’écran.</p><br /><p align=\"center\">Vous devez identifier le symbole (%stim2%)<br />en appuyant sur la touche du clavier correspondante.</p></html>");	
			else{
				if(Task.mainTask.getVersion() == 6)	// gauche/droite
					expression.put("keyGeneralExplanationHeader", "<html><p align=\"center\">Pour ce bloc, %stim1% se feront entendre.</p><br /><p align=\"center\">Vous devez identifier d'où provient le son (gauche ou droite)<br />en appuyant sur la touche du clavier correspondante.</p></html>");
				else if(Task.mainTask.getVersion() == 7)	//fréquence
					expression.put("keyGeneralExplanationHeader", "<html><p align=\"center\">Pour ce bloc, %stim1% se feront entendre.</p><br /><p align=\"center\">Vous devez identifier les sons<br />en appuyant sur la touche du clavier correspondante.</p></html>");
				
			}
			
			expression.put("keyGeneralExplanationFooter", "<html><p align=\"center\">Vous devez répondre <b>le plus vite possible et faire le moins<br />d’erreurs possible.</b></p></html>");
			
			if(Task.mainTask.getVersion() == 6)
				expression.put("keyGeneralExplanationHeader2", "<html><p align=\"center\">Pour ce bloc, %stim1% se feront entendre.</p><br /><p align=\"center\">Vous devez identifier le mot (GO ou STOP)<br />en appuyant sur la touche du clavier correspondante.</p></html>");
			else
				expression.put("keyGeneralExplanationHeader2", "<html><p align=\"center\">Pour ce bloc, %stim1% se feront entendre.</p><br /><p align=\"center\">Vous devez identifier les sons<br />en appuyant sur la touche du clavier correspondante.</p></html>");
			
			
			// DetailedExplanation
			if(! Task.mainTask.isStimsAreSounds())
				expression.put("keyDetailedExplanationHeader", "<html><p align=\"center\">Si %fullname% apparait au centre de l'écran,<br />appuyez sur la touche <b>%key%</b> avec votre %finger%.</p></html>");
			else
				expression.put("keyDetailedExplanationHeader", "<html><p align=\"center\">Si %fullname% se fait entendre,<br />appuyez sur la touche <b>%key%</b> avec votre %finger%.</p></html>");
			expression.put("keyDetailedExplanationFooter", "<html><p align=\"center\">Pour continuer, appuyez sur la touche %key%</b></p></html>");
			
			
			
			Utilities.iniHashMap(expression, "overviewError", new String[]{"header", "footer"}, new String[]{ "<html><p align='center'>Une erreur s'est produite à l'affichage du graphique.<br /> Si cette erreur se reproduit lors de votre prochain entrainement, nous vous invitons à contacter un assistant de recherche!</p></html>",  "<html><p align='center'>Désolé pour les inconvénients! :)</p></html>"});


			// S&DMixteHeader
			if(! Task.mainTask.isStimsAreSounds()){
				expression.put("keyS&DMixteHeader", "<html><p align=\"center\">Dans cet exercice, des %taskName% <b>%link%</b> des %otherTaskName% vont apparaître au<br />" +
						"centre de l'écran.</p>" +
						"<br />" +
						"<p align=\"center\">Vous devez identifier le symbole en appuyant sur les touches correpondantes<br />" +
						"correspondante.</p></html>");
			}else{
				expression.put("keyS&DMixteHeader", "<html><p align=\"center\">Dans cet exercice, des %taskName% <b>%link%</b> des %otherTaskName% vont se faire entendre</p>" +
						"<br />" +
						"<p align=\"center\">Vous devez identifier le son en appuyant sur la touche<br />" +
						"correspondante.</p></html>");
			}
			expression.put("keyS&DMixteFooter", "<html><p align=\"center\">Vous devez répondre <b>le plus vite possible et faire le moins<br />" +
					"d’erreurs possible.</b></p>" +
					"<p align=\"center\">Appuyez sur la barre d’espacement pour avancer dans le menu.</p></html>");
		
			
			expression.put("nBackExplanationHeader1Back", "<html><p align=\"center\"><b>Mais attention! Vous ne devez pas répondre à l'image " +
					"que vous voyez, mais à l'image qui la précédait.</b></p>" +
					"<br />" +
					"<p align=\"center\">Voici un exemple:</p></html>");
			
			expression.put("nBackExplanationHeader2Back", "<html><p align=\"center\"><b>Mais attention! Vous ne devez pas répondre à l'image " +
					"que vous voyez, mais à l'avant dernière image vue.</b></p>" +
					"<br />" +
					"<p align=\"center\">Voici un exemple:</p></html>");
			
			expression.put("nBackAsteriskHeader", "<html><p align=\"center\">Entre chaque symbole, un \"+\" apparaîtra<br />" +
					"au centre de l'écran, comme suit :</p></html>");
			expression.put("nBackAsteriskFooter1Back", "<html><p align=\"center\">Les \"+\" vous indiquent les moments où aucune<br />" +
					"réponse n'est attendue.</p>" +
					"<br />" + 
					"<p align=\"center\">De plus, vous n'avez rien à répondre au premier<br />" +
					"symbole, car il faut répondre l'image<br />" +
					"vue un essai auparavant!</p>" +
					"<br />" +
					"<p align=\"center\">Donc, pour la première image, ne répondez tout simplement pas et retenez l'image pour donnez votre réponse à l'essai suivant.</html>");
			
			expression.put("nBackAsteriskFooter2Back", "<html><p align=\"center\">Les \"+\" vous indiquent les moments où aucune<br />" +
					"réponse n'est attendue.</p>" +
					"<br />" + 
					"<p align=\"center\">De plus, vous n'avez rien à répondre au premier<br />" +
					"symbole, ni au deuxième, car il faut répondre l'image<br />" +
					"vue 2 essais auparavant!</p>" +
					"<br />" +
					"<p align=\"center\">Donc, pour les deux premières images, ne répondez pas et retenez les images pour y répondre deux essais plus tard.</p></html>");
			
			expression.put("allStimuliHeader", "<html><br /><p align=\"center\">Prenez note que chaque symbole peut légèrement varier comme suit :</p></html>");
			expression.put("allStimuliFooter", "<html><p align=\"center\">Appuyez sur la barre d’espacement pour avancer dans le menu.</p></html>");
		
			expression.put("feedbackHeader", "<html><p align=\"center\">Des informations concernant vos performances vont vous être données.</p></html>");
			expression.put("feedbackFooter",  "<html><p align=\"center\"><b>La barre de gauche</b> indique la vitesse de votre main gauche.<br/>" +
					"<b>La barre de droite</b> indique la vitesse de votre main droite.<br/>" +
					"<b>Tenter de maintenir votre performance dans la zone orange<br/>" +
					"ou mieux</b></p></html>");

			expression.put("prioriteHeader", "<html><p align=\"center\">Cet exercice est similaire au précédent à une exception près:<br />" +
					"Vous devez <b>donner la priorité à votre main gauche</b> et <b>moins<br/>" +
					"porter attention à votre main droite.<b/></p><br/>" +
					"<p align=\"center\">Pour vous aider, le feedback concernant vos performances sera<br/>" +
					"ajusté. Pour avoir du bon feedback, <b>vous devrez être plus rapide<br/>" +
					"avec votre main gauche</b></p><br/>" +
					"<p align=\"center\">Par ailleurs, <b>vous pourrez être plus lent avec votre main droite</b><br/>" +
					"sans que votre feedback avec cette main soit mauvais</p><br />" +
					"<p align=\"center\"><b>Tenter de maintenir votre performance dans la zone orange<br/>" +
					"ou mieux</b></p></html>");
			expression.put("prioriteFooter",  "<html><p align=\"center\"><b>La barre de gauche</b> indique la vitesse de votre main gauche.<br/>" +
					"<b>La barre de droite</b> indique la vitesse de votre main droite.<br/>" +
					"<b>Tenter de maintenir votre performance dans la zone orange<br/>" +
					"ou mieux</b></p></html>");
			
			Utilities.iniHashMap(expression, "pause", new String[]{"header", "footer"}, new String[]{
					"<html><p align=\"center\">Le prochain bloc est un bloc de pause qui vise à mesurer votre activité cérébrale au repos.</p></html>", "<html><p align=\"center\">Nous vous demandons de bien vouloir fixé le centre de l'écran. <br/>S.V.P. évitez de penser à la tâche.</p></html>"});		
			
			expression.put("goodbyeCenter", "<html><p align=\"center\"><b>Cette étape est terminée! Bon boulot !<br/><br/>Merci de votre temps!</b><br/>Vous pouvez maintenant fermer la fenêtre.</p></html>");

			
			expression.put("and", "ET");
			expression.put("or", "OU");
			
			expression.put("reminderExplanation1", "Vous pouvez consulter les rappels au bas de l'écran/n en tout temps, mais votre regard doit être maintenu/n au centre de l'écran lorsque la croix apparait.");
			expression.put("reminderExplanation2", "Les images apparaitront à l'endroit où se trouve la croix ci-bas.");
			expression.put("reminderExplanation3", "Pour commencer, appuyez/n sur la barre d'espacement.");

			
			
			expression.put("urgency", "Arrêt forcé! Appuyer sur ESC pour reprendre.");

			
			expression.put("overviewSpeedTitle", "Progrès de la Rapidité");
			expression.put("overviewSpeedX", "Session");
			expression.put("overviewSpeedY", "Rapidité");
			expression.put("overviewStim", "Stimuli :");
			expression.put("overviewTask", "Tâche ");
			
			
			expression.put("overviewAccTitle", "Progrès de la Précision");
			expression.put("overviewAccX", "Session");
			expression.put("overviewAccY", "Précision");
			expression.put("overviewStim", "Stimuli :");
			expression.put("overviewTask", "Tâche ");
			
			expression.put("doNotAnswer", "Ne répondez pas maintenant!");

			
			//Philippe du et des hardcodés ?!
			Utilities.iniHashMap(expression, "overview", new String[]{"preSpeedHeader",  "speedFooter", "accHeader", "accFooter",  "postSpeedHeader"},
					new String[]{"<html><p align=\"center\">Avant de commencer votre entrainement, prenez le temps de voir vos performances au cours du(des) dernier(s) entrainements.</p></html>", "<html><p align=\"center\">Appuyez sur la barre d'espacement pour examiner vos performances <br /> en ce qui à trait à <b>votre rapidité à exécuter deux tâches simultanément</b>.</p></html>",
					"<html><p align=\"center\">Maintenant, prenez le temps d'examiner aussi vos performances <br />en ce qui à trait à <b>votre précision (pourcentage de bonnes réponses).</b></p></html>", "<html><p align=\"center\">Appuyez sur la barre d'espacement pour visionner le graphique.</p></html>",
					"<html><p align=\"center\"><b>L'entrainement est terminé!</b> <br />Il est temps de voir <b>si vous vous êtes amélioré</b> <br /> depuis votre dernier entrainement.</p></html>"});

			expression.put("decalage", " Vos doigts sont mal placés!");
			
			Utilities.iniHashMap(expression, "finger", new String[]{"0", "1", "2", "3"}, new String[]{"auriculaire", "annulaire", "majeur", "index"});


			expression.put("taskName1", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskName1"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"étoile", "soleil", "lune", "planète"});
			Utilities.iniHashMap(expression.get("taskName1"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"\"E\"", "\"U\"", "\"A\"", "\"O\""});
			
			Utilities.iniHashMap(expression.get("taskName1"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"\"bêta (ß)\"", "\"pi(π)\"", "\"omega (Ω)\"", "\"sigma (∑)\""});
			
			//Utilities.iniHashMap(expression.get("taskName1"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"\"ß\"", "\"p\"", "\"Ω\"", "\"∑\""});
			Utilities.iniHashMap(expression.get("taskName1"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"dé-3", "dé-1", "dé-6", "dé-2"});
			Utilities.iniHashMap(expression.get("taskName1"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"});
			Utilities.iniHashMap(expression.get("taskName1"), "5", new String[]{"0", "1"}, new String[]{"son à gauche", "son à droite"});
			Utilities.iniHashMap(expression.get("taskName1"), "6", new String[]{"0", "1", "2"}, new String[]{"grave", "aigu", "moyen"});
			
			
			expression.put("taskName2", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskName2"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"bas", "droite", "gauche",  "haut"});
			Utilities.iniHashMap(expression.get("taskName2"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"rouge", "bleu", "jaune", "vert"});
			Utilities.iniHashMap(expression.get("taskName2"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"cercle", "carré", "losange", "octogone"});
			Utilities.iniHashMap(expression.get("taskName2"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"unie", "ondulée", "quadrillée", "rayée"});
			Utilities.iniHashMap(expression.get("taskName2"), "4", new String[]{"0", "1", "2", "3"}, new String[]{" 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9"});
			Utilities.iniHashMap(expression.get("taskName2"), "5", new String[]{"0", "1"}, new String[]{"go", "stop"});
			Utilities.iniHashMap(expression.get("taskName2"), "6", new String[]{"0", "1", "2"}, new String[]{"i", "o","a"});
			
			expression.put("taskFullName1", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskFullName1"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"une étoile", "un soleil", "une lune", "une planète"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"un E", "un U", "un A", "un O"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"un ß (bêta)", "un π (pi)", "un Ω (omega)", "un ∑ (sigma)"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"un dé-3", "un dé-1", "un dé-6", "un dé-2"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"un 1", "un 2", "un 3", "un 4", "un 5", "un 6", "un 7", "un 8", "un 9"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "5", new String[]{"0", "1"}, new String[]{"un son provenant de la gauche", "un son provenant de la droite"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "6", new String[]{"0", "1", "2"}, new String[]{"un son grave", "un son aigu", " un son de fréquence moyenne"});
			
			expression.put("taskFullName2", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskFullName2"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"une flèche dont le rouge pointe vers le bas", "une flèche dont le rouge pointe vers la droite", "une flèche dont le rouge pointe vers la gauche", "une flèche dont le rouge pointe vers le haut"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"le rouge", "le bleu", "le jaune", "le vert"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"un cercle", "un carré", "un losange", "un octogone"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"une forme unie", "une forme ondulée", "une forme quadrillée", "une forme rayée"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"un 1", "un 2", "un 3", "un 4", "un 5", "un 6", "un 7", "un 8", "un 9"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "5", new String[]{"0", "1"}, new String[]{"le mot GO","le mot STOP"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "6", new String[]{"0", "1", "2"}, new String[]{"la voyelle /i/", "la voyelle /o/", "la voyelle /a/"});
			
			
			stims.put("taskName1", new String[][]{
					{"étoile", "soleil", "lune", "planète"},
					{"\"E\"", "\"U\"", "\"A\"", "\"O\""}, 
					{"\"ß\"", "\"π\"", "\"Ω\"", "\"∑\""}, 
					{"dé-3", "dé-1", "dé-6", "dé-2"}, 
					{"1", "2", "3", "4", "5", "6", "7", "8", "9"},
					{"son à gauche", "son à droite"},
					{"grave", "aigu", "moyen"}
			});

			stims.put("taskName2", new String[][]{
					{"bas", "haut", "droite",  "gauche", },  
					{"rouge", "bleu", "jaune", "vert"}, 
					{"cercle", "carré", "losange", "octogone"}, 
					{"unie", "ondulée", "quadrillée", "rayée"}, 
					{" 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9"},
					{"go", "stop"},
					{"i", "o", "a"}
			});

			stims.put("taskFullName1", new String[][]{
					{"une étoile", "un soleil", "une lune", "une planète"}, 
					{"un E", "un U", "un A", "un O"},
					{"un ß (bétâ)", "un π (pi)", "un Ω (omega)", "un ∑ (sigma)"},
					{"un dé-3", "un dé-1", "un dé-6", "un dé-2"},
					{"un 1", "un 2", "un 3", "un 4", "un 5", "un 6", "un 7", "un 8", "un 9"},
					{"un son provenant de la gauche", "un son provenant de la droite"},
					{"un son grave", "un son aigu", " un son de fréquence moyenne"}
			});

			stims.put("taskFullName2", new String[][]{
					{"une flèche dont le rouge pointe vers le bas", "une flèche dont le rouge pointe vers le haut", "une flèche dont le rouge pointe vers la droite", "une flèche dont le rouge pointe vers la gauche"}, 
					{"le rouge", "le bleu", "le jaune", "le vert"},
					{"un cercle", "un carré", "un losange", "un octogone"}, 
					{"une forme unie", "une forme ondulée", "une forme quadrillée", "une forme rayée"},
					{"un 1", "un 2", "un 3", "un 4", "un 5", "un 6", "un 7", "un 8", "un 9"},
					{"le mot GO","le mot STOP"},
					{"la voyelle /i/", "la voyelle /o/", "la voyelle /a/"}
			});
				
		}
		if (langue == "english")
		{		
			Utilities.iniHashMap(expression, "links", new String[]{"&", "|"}, new String[]{"AND", "OR"});				

			Utilities.iniHashMap(expression, "tasksNames", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"HEAVENLY BODY", "LETTERS", "GREEK LETTERS", "QUANTITY", "PHILLIPS", "LEFT/RIGHT SOUND", "PITCHES"});				
			Utilities.iniHashMap(expression, "otherTasksNames", new String[]{"0", "1", "2", "3", "4", "5", "6"}, new String[]{"ARROWS", "COLORS", "SHAPES", "STYLES", "f", "GO/STOP", "VOWELS"});		
			
			
			Utilities.iniHashMap(expression, "intro", new String[]{"introCenter", "noIRMFooter", "IRMFooter"}, new String[]{
					"HELLO!", "Ready ? Please press the space bar to begin the first block.", "<html><p align=\"center\">Please wait while the task synchronizes itself with the imaging device.</p></html>"});		
			
			expression.put("introFooter", "<html><p align=\"center\">Press the space bar to go forward in the menu.</p></html>");

			
			
			
			// GeneralExplanation
			if(! Task.mainTask.isStimsAreSounds())
				expression.put("keyGeneralExplanationHeader", "<html><p align=\"center\">For this bloc, %stim1% will appear at<br /> the centre of the screen.</p><br /><p align=\"center\">You must identify the symbol (%stim2%)<br />by pressing on the corresponding key.</p></html>");
			else{
				expression.put("keyGeneralExplanationHeader", "<html><p align=\"center\">For this bloc, %stim1% will be heard.</p><br /><p align=\"center\">You must identify from where the sound is coming<br />by pressing on the corresponding key.</p></html>");
			}
			expression.put("keyGeneralExplanationFooter", "<html><p align=\"center\">You must answer <b>as quickly as possible while making as few<br />errors possible.</b></p></html>");
			expression.put("keyGeneralExplanationHeader2", "<html><p align=\"center\">For this bloc, %stim1% will be heard.</p><br /><p align=\"center\">You must identify the word (Go or STOP)<br />by pressing on the corresponding key.</p></html>");
			
			// DetailedExplanation
			if(! Task.mainTask.isStimsAreSounds())
				expression.put("keyDetailedExplanationHeader", "<html><p align=\"center\">If %fullname% appears at the centre of the screen,<br />press the <b>%key%</b> key with your %finger%.</p></html>");
			else
				expression.put("keyDetailedExplanationHeader", "<html><p align=\"center\">If %fullname% is heard,<br />press the <b>%key%</b> key with your %finger%.</p></html>");
			expression.put("keyDetailedExplanationFooter", "<html><p align=\"center\">To continue, press the %key% key.</b></p></html>");
			

			// S&DMixteHeader
			if(! Task.mainTask.isStimsAreSounds()){
				expression.put("keyS&DMixteHeader", "<html><p align=\"center\">In this task, %taskName% <b>%link%</b> %otherTaskName% will appear at the<br />" +
						"centre of the screen.</p>" +
						"<br />" +
						"<p align=\"center\">You must identify the symbol by pressing on the corresponding<br />" +
						"key.</p></html>");
			}else{
				expression.put("keyS&DMixteHeader", "<html><p align=\"center\">Dans cet exercice, des %taskName% <b>%link%</b> des %otherTaskName% vont se faire entendre</p>" +
						"<br />" +
						"<p align=\"center\">Vous devez identifier le symbole en appuyant sur la touche<br />" +
						"correspondante.</p></html>");
				expression.put("keyS&DMixteHeader", "<html><p align=\"center\">In this task, %taskName% <b>%link%</b> %otherTaskName% will be heard<br />" +
						"<p align=\"center\">You must identify the sound by pressing on the corresponding<br />" +
						"key.</p></html>");
			}

			
			Utilities.iniHashMap(expression, "overviewError", new String[]{"header", "footer"}, new String[]{ "<html><p align='center'>An error has occurred in the graphic display.<br /> If this error reoccurs during your next training, we suggest that you contact a research assistant!</p></html>",  "<html><p align='center'>Sorry for the inconvenience! :)</p></html>"});


			
			
			expression.put("keyS&DMixteFooter", "<html><p align=\"center\">You must answer <b>as quickly as possible while making as few<br />" +
					"errors as possible.</b></p>" +
					"<p align=\"center\">Press on the space bar to go forward in the menu.</p></html>");
			
			expression.put("nBackExplanationHeader1Back", "<html><p align=\"center\"><b>But be careful! You must not respond to the image " +
					"that you see, but rather to the preceding image.</b></p>" +
					"<br />" +
					"<p align=\"center\">Here is an example:</p></html>");
			
			expression.put("nBackExplanationHeader2Back", "<html><p align=\"center\"><b>But be careful! You must not respond to the image " +
					"that you see, but rather to the image shown two trials previously.</b></p>" +
					"<br />" +
					"<p align=\"center\">Here is an example:</p></html>");
			
			expression.put("nBackAsteriskHeader", "<html><p align=\"center\">Between each symbol, a \"+\" will appear<br />" +
					"at the centre of the screen, as follows :</p></html>");
			expression.put("nBackAsteriskFooter1Back", "<html><p align=\"center\">The \"+\" will indicate the moments when no<br />" +
					"response is expected.</p>" +
					"<br />" + 
					"<p align=\"center\">In addition, no answer is required for the first<br />" +
					"symbol, since you must respond to the image<br />" +
					"seen in the previous trial!</p>" +
					"<br />" +
					"<p align=\"center\">So, for the first image, simply do not respond and keep the image in mind in order to respond in the next trial.</html>");
			
			expression.put("nBackAsteriskFooter2Back", "<html><p align=\"center\">The \"+\" indicate the moments when no<br />" +
					"response is expected.</p>" +
					"<br />" + 
					"<p align=\"center\">In addition, no answer is required for the first<br />" +
					"symbol, nor for the second one, since you must respond to the image<br />" +
					"seen 2 trials previously!</p>" +
					"<br />" +
					"<p align=\"center\">So, for the first two images, do not respond and hold the images in mind in order to respond two trials later.</p></html>");
			
			expression.put("allStimuliHeader", "<html><br /><p align=\"center\">Please note that each symbol may slightly vary as follows :</p></html>");
			expression.put("allStimuliFooter", "<html><p align=\"center\">Press on the space bar to go forward in the menu.</p></html>");
		
			expression.put("feedbackHeader", "<html><p align=\"center\">Information concerning your performance will be provided to you.</p></html>");
			expression.put("feedbackFooter",  "<html><p align=\"center\"><b>The bar on the left</b> indicates the speed of your left hand.<br/>" +
					"<b>The bar on the right</b> indicates the speed of your right hand.<br/>" +
					"<b>Try to keep you performance in the orange zone<br/>" +
					"or better</b></p></html>");

			expression.put("prioriteHeader", "<html><p align=\"center\">This task is similar to the preceding one save for one exception:<br />" +
					"You must <b>prioritize your left hand</b> and pay <b>less<br/>" +
					"attention to your right hand.<b/></p><br/>" +
					"<p align=\"center\">To help you, the feedback concerning your performance will be<br/>" +
					"adjusted. To get good feedback, <b>you will have to be quicker<br/>" +
					"with your left hand</b></p><br/>" +
					"<p align=\"center\">Furthermore, <b>you can be slower with your right hand</b><br/>" +
					"without getting negative feedback on that hand.</p><br />" +
					"<p align=\"center\"><b>Try to keep your performance in the orange zone<br/>" +
					"or better</b></p></html>");
			expression.put("prioriteFooter",  "<html><p align=\"center\"><b>The bar on the left</b> indicates the speed of your left hand.<br/>" +
					"<b>The bar on the right</b> indicates the speed of your right hand.<br/>" +
					"<b>Try to keep your performance in the orange zone<br/>" +
					"or better</b></p></html>");
			
			Utilities.iniHashMap(expression, "pause", new String[]{"header", "footer"}, new String[]{
					"<html><p align=\"center\">The next block is a break that intends to measure your cerebral activity at rest.</p></html>", "<html><p align=\"center\">We ask that you focus on the centre of the screen. <br/>Please avoid thinking about the task.</p></html>"});		
			
			expression.put("goodbyeCenter", "<html><p align=\"center\"><b>This step is finished! Good work !<br/><br/>Thank you for your time!</b><br/>You may now close the window.</p></html>");

			
			expression.put("and", "AND");
			expression.put("or", "OR");
			
			expression.put("reminderExplanation1", "You may consult the reminders at the bottom of the screen/n at any  time, but your sight must be kept at the centre /nwhen the cross appears.");
			expression.put("reminderExplanation2", "The images will appear at the location where the cross below is situated.");
			expression.put("reminderExplanation3", "Press the space bar to begin!");

			
			
			expression.put("urgency", "Forced stop! Press ESC to resume.");

			
			expression.put("overviewSpeedTitle", "Speed progress");
			expression.put("overviewSpeedX", "Session");
			expression.put("overviewSpeedY", "Speed");
			expression.put("overviewStim", "Stimuli :");
			expression.put("overviewTask", "Task ");
			
			
			expression.put("overviewAccTitle", "Accuracy progress");
			expression.put("overviewAccX", "Session");
			expression.put("overviewAccY", "Accuracy");
			expression.put("overviewStim", "Stimuli :");
			expression.put("overviewTask", "Task ");
			
			expression.put("doNotAnswer", "Do not answer now!");

			
			//Philippe du et des hardcodés ?!
			Utilities.iniHashMap(expression, "overview", new String[]{"preSpeedHeader",  "speedFooter", "accHeader", "accFooter",  "postSpeedHeader"},
					new String[]{"<html><p align=\"center\">Before starting your training, take the time to see your performances during the last training(s).</p></html>", "<html><p align=\"center\">Press the space bar to look at your performances<br /> in terms of <b>your speed in executing two simultaneous tasks</b>.</p></html>",
					"<html><p align=\"center\">Now, take the time to look at your performances <br />in terms of<b>your accuracy (percent of correct answers).</b></p></html>", "<html><p align=\"center\">Press the space bar to see the graphic.</p></html>",
					"<html><p align=\"center\"><b>The training is finished!</b> <br />It is time to see <b>if you have improved</b> <br /> since the start of the training.</p></html>"});

			expression.put("decalage", " Your fingers are wrongly positionned!");
			
			Utilities.iniHashMap(expression, "finger", new String[]{"0", "1", "2", "3"}, new String[]{"little finger", "ring finger", "middle finger", "index"});

			expression.put("taskName1", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskName1"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"star", "sun", "moon", "planet"});
			Utilities.iniHashMap(expression.get("taskName1"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"\"E\"", "\"U\"", "\"A\"", "\"O\""});
			Utilities.iniHashMap(expression.get("taskName1"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"\"ß\"", "\"p\"", "\"Ω\"", "\"∑\""});
			Utilities.iniHashMap(expression.get("taskName1"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"3 dots dice", "1 dots dice", "6 dots dice", "2 dots dice"});
			Utilities.iniHashMap(expression.get("taskName1"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"});
			Utilities.iniHashMap(expression.get("taskName1"), "5", new String[]{"0", "1"}, new String[]{"left sound", "right sound"});
			Utilities.iniHashMap(expression.get("taskName1"), "6", new String[]{"0", "1", "2"}, new String[]{"low", "high", "medium"});

			
			expression.put("taskName2", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskName2"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"down", "right", "left",  "up"});
			Utilities.iniHashMap(expression.get("taskName2"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"red", "blue", "yellow", "green"});
			Utilities.iniHashMap(expression.get("taskName2"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"circle", "square", "diamond", "octogon"});
			Utilities.iniHashMap(expression.get("taskName2"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"plain", "rippled", "squared", "striped"});
			Utilities.iniHashMap(expression.get("taskName2"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"});
			Utilities.iniHashMap(expression.get("taskName2"), "5", new String[]{"0", "1"}, new String[]{"go", "stop"});
			Utilities.iniHashMap(expression.get("taskName2"), "6", new String[]{"0", "1", "2"}, new String[]{"i", "o", "a"});

			
			expression.put("taskFullName1", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskFullName1"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"a star", "a sun", "a moon", "a planet"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"a E", "a U", "a A", "a O"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"a ß (bétâ)", "a p (pi)", "a Ω (omega)", "a ∑ (sigma)"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"a 3 dots dice", "a 1 dot dice", "a 6 dots dice", "a 2 dots dice"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"a 1", "a 2", "a 3", "a 4", "a 5", "a 6", "a 7", "a 8", "a 9"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "5", new String[]{"0", "1"}, new String[]{"a sound coming from left", "a sound coming from right"});
			Utilities.iniHashMap(expression.get("taskFullName1"), "5", new String[]{"0", "1", "2"}, new String[]{"a high-pitched sound", "a low-pitched sound", "a medium-pitched sound"});

			
			expression.put("taskFullName2", new  HashMap <String, Object>() );
			Utilities.iniHashMap(expression.get("taskFullName2"), "0", new String[]{"0", "1", "2", "3"}, new String[]{"an arrow pointing down", "an arrow pointing right", "an arrow pointing left", "an arrow pointing top"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "1", new String[]{"0", "1", "2", "3"}, new String[]{"a red shape", "a blue shape", "a yellow shape", "a green shape"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "2", new String[]{"0", "1", "2", "3"}, new String[]{"a circle", "a square", "a diamond", "an octogon"});
			Utilities.iniHashMap(expression.get("taskFullName2"), "3", new String[]{"0", "1", "2", "3"}, new String[]{"a plain shape", "a rippled shape", "a squared shape", "a striped shape "});
			Utilities.iniHashMap(expression.get("taskFullName2"), "4", new String[]{"0", "1", "2", "3"}, new String[]{"a 1", "a 2", "a 3", "a 4", "a 5", "a 6", "a 7", "a 8", "a 9"});	
			Utilities.iniHashMap(expression.get("taskFullName2"), "5", new String[]{"0", "1"}, new String[]{"the word GO", "the word STOP"});	
			Utilities.iniHashMap(expression.get("taskName2"), "6", new String[]{"0", "1", "2"}, new String[]{"the vowel /i/", "the vowel /o/", "the vowel /a/"});
			
			
			stims.put("taskName1", new String[][]{
					{"star", "sun", "moon", "planet"},
					{"\"E\"", "\"U\"", "\"A\"", "\"O\""}, 
					{"\"ß\"", "\"p\"", "\"Ω\"", "\"∑\""}, 
					{"3 dots dice", "1 dots dice", "6 dots dice", "2 dots dice"}, 
					{"1", "2", "3", "4", "5", "6", "7", "8", "9"},
					{"left sound", "right sound"},
					{"low", "high", "medium"}
			});
			
			stims.put("taskName2", new String[][]{
					{"down", "up", "right",  "left"},  
					{"red", "blue", "yellow", "green"}, 
					{"circle", "square", "diamond", "octogon"}, 
					{"plain", "rippled", "squared", "striped"}, 
					{" 1", " 2", " 3", " 4", " 5", " 6", " 7", " 8", " 9"},
					{"go", "stop"},
					{"i", "o", "a"}
			});
	
			stims.put("taskFullName1", new String[][]{
					{"a star", "a sun", "a moon", "a planet"}, 
					{"a E", "a U", "a A", "a O"},
					{"a ß (bétâ)", "a p (pi)", "a Ω (omega)", "a ∑ (sigma)"},
					{"a 3 dots dice", "a 1 dot dice", "a 6 dots dice", "a 2 dots dice"},
					{"a 1", "a 2", "a 3", "a 4", "a 5", "a 6", "a 7", "a 8", "a 9"},
					{"a sound coming from left","a sound coming from right"},
					{"a high-pitched sound", "a low-pitched sound", "a medium-pitched sound"}
			});

			stims.put("taskFullName2", new String[][]{
					{"an arrow pointing down", "an arrow pointing up", "an arrow pointing right", "an arrow pointing left"}, 
					{"a red shape", "a blue shape", "a yellow shape", "a green shape"},
					{"a circle", "a square", "a diamond", "an octogon"}, 
					{"a plain form", "a rippled form", "a squared form", "a striped form "},
					{"a 1", "a 2", "a 3", "a 4", "a 5", "a 6", "a 7", "a 8", "a 9"},
					{"the word GO", "the word STOP"},
					{"the vowel /i/", "the vowel /o/", "the vowel /a/"}
			});
		}
		
	}

	public static String translate(String[] key, HashMap<String, String> args)
	{
		String returnObject = "Display Error";
		HashMap<String, Object> temp = expression;
		

		
		for (int i = 0; i<key.length;i++)
		{
			Object object = temp.get(key[i]);
			
			if (object instanceof String)
			{
				returnObject = (String) object;
			}
			else if (object instanceof HashMap)
			{
				temp = (HashMap<String, Object>) object;
			}	
		}
		if (args != null)
		{
			for (Map.Entry<String, String> entry : args.entrySet()) {
				String tag = entry.getKey();
				String value = entry.getValue();
				returnObject = returnObject.replace("%" + tag + "%", value);
			}
		}
		return returnObject;
	}
	
	public static String translate(String[] key)
	{
		return translate(key, null);
	}
	public static String translate(String key)
	{
		return translate(key, null);
	}
	public static String translate(String key, HashMap<String, String> args)
	{
		return translate(new String[]{key}, args);
	}
	
	public static void setMatching (String langue)
	{			
		if (langue == "francais")
		{
			expression.put("matching0back", "D= # cible, F= # autre");
			expression.put("matchingInstG", "D=Pareil, F= Différent");
			expression.put("matchingInstD", "K=Pareil, L= Différent");
			expression.put("allStimuliHeader", "<html><br /><p align=\"center\">Lors de cette exercice, %stim1% vont apparaître au centre de l'écran. Les symboles (%stim2%) peuvent légèrement varier comme suit :</p></html>");
			expression.put("allStimuliFooter", "<html><p align=\"center\">Appuyez sur la barre d’espacement pour avancer dans le menu.</p></html>");
			
			/*expression.put("nBackExplanationHeader1Back", "<html><p align=\"center\"><b>Votre tâche est de déterminer si l'image que vous voyez " +
					"est la même que celle qui la précédait.</b></p>" +
					"<br />" +
					"<p align=\"center\">Voici un exemple:</p></html>");
			
			expression.put("nBackExplanationHeader2Back", "<html><p align=\"center\"><b>Votre tâche est de déterminer si l'image que vous voyez " +
					"est la même que l'avant dernière image vue.</b></p>" +
					"<br />" +
					"<p align=\"center\">Voici un exemple:</p></html>");
			
			expression.put("nBackExplanationHeader3Back", "<html><p align=\"center\"><b>Votre tâche est de déterminer si l'image que vous voyez " +
					"est la même que l'avant-avant dernière image vue.</b></p>" +
					"<br />" +
					"<p align=\"center\">Voici un exemple:</p></html>");*/
			
					expression.put("nBackExplanationHeader0Back", "<html><p align=\"center\">Votre tâche est de déterminer si l'image que vous voyez " +
							"est un 3." +
							"<br />" +
							"Si un 3 apparaît, appuyez sur sur la touche <b>%key1%</b>. Sinon appuyez sur la touche <b>%key2%</b>.</p></html>");
			
			
					expression.put("nBackExplanationHeader1Back", "<html><p align=\"center\">Votre tâche est de déterminer si l'image que vous voyez " +
							"est la même que celle qui la précédait." +
							"<br />" +
							"Si les deux symboles sont de même nature (ils peuvent varier légèrement sur la couleur ou la forme), appuyez sur sur la touche <b>%key1%</b>. Sinon appuyez sur la touche <b>%key2%</b>.</p></html>");
			
					expression.put("nBackExplanationHeader2Back", "<html><p align=\"center\">Votre tâche est de déterminer si l'image que vous voyez " +
							"est la même que l'avant dernière image vue." +
							"<br />" +
							"Si les deux symboles sont de même nature (ils peuvent varier légèrement sur la couleur ou la forme), appuyez sur sur la touche <b>%key1%</b>. Sinon appuyez sur la touche <b>%key2%</b>.</p></html>");
					
					expression.put("nBackExplanationHeader3Back", "<html><p align=\"center\">Votre tâche est de déterminer si l'image que vous voyez " +
							"est la même que l'avant-avant dernière image vue." +
							"<br />" +
							"Si les deux symboles sont de même nature (ils peuvent varier légèrement sur la couleur ou la forme), appuyez sur sur la touche <b>%key1%</b>. Sinon appuyez sur la touche <b>%key2%</b>.</p></html>");	
			
			expression.put("allStimuliNbackSPHeader", "<html><p align=\"center\">Si les deux symboles sont de même nature (ils peuvent varier légèrement sur la couleur ou la forme), appuyez sur sur la touche <b>%key1%</b>. Sinon appuyez sur la touche <b>%key2%</b>.</p></html>");
	
			expression.put("nBackAsteriskFooter1Back", "<html><p align=\"center\">Les \"+\" vous indiquent que l'essai est terminé<br />" +
					"et que vous ne pouvez plus répondre.</p>" +
					"<br />" + 
					"<p align=\"center\">De plus, vous n'avez rien à répondre au premier<br />" +
					"symbole, car il n'y a pas encore<br />" +
					"eu d'image qui la précédait!</p>" +
					"<br />" +
					"<p align=\"center\">Donc, pour la première image, ne répondez tout simplement pas et retenez l'image pour dire si elle est comme la précédente.</html>");
			
			expression.put("nBackAsteriskFooter2Back", "<html><p align=\"center\">Les \"+\" vous indiquent les moments où aucune<br />" +
					"réponse n'est attendue.</p>" +
					"<br />" + 
					"<p align=\"center\">De plus, vous n'avez rien à répondre au premier<br />" +
					"symbole, ni au deuxième<br />" +
					"<br />" +
					"<p align=\"center\">Donc, pour les deux premières images, ne répondez pas et retenez les images pour les comparer aux suivantes.</p></html>");
			
			expression.put("nBackAsteriskFooter3Back", "<html><p align=\"center\">Les \"+\" vous indiquent les moments où aucune<br />" +
					"réponse n'est attendue.</p>" +
					"<br />" + 
					"<p align=\"center\">De plus, vous n'avez rien à répondre au premier<br />" +
					"duexième et troisième symboles<br />" +
					"<br />" +
					"<p align=\"center\">Donc, pour les trois premières images, ne répondez pas et retenez les images pour les comparer aux suivantes.</p></html>");

		}
		if (langue == "english")
		{
			expression.put("matching0back", "D= target #, F= other #");
			expression.put("matchingInstG", "D=Same, F= Different");
			expression.put("matchingInstD", "K=Same, L= Different");
			expression.put("allStimuliHeader", "<html><br /><p align=\"center\">During this exercise, %stim1% will appear in the centre of the screen. Symbols (%stim2%) can vary as shown below :</p></html>");
			expression.put("allStimuliFooter", "<html><p align=\"center\">Press the spacebar to continue.</p></html>");
			/*expression.put("nBackExplanationHeader1Back", "<html><p align=\"center\"><b>Your task is to determine if the symbol you see  " +
					"is similar to the preceding one (-1).</b></p>" +
					"<br />" +
					"<p align=\"center\">Here is an example:</p></html>");
			
			expression.put("nBackExplanationHeader2Back", "<html><p align=\"center\"><b>Your task is to determine if the symbol you see  " +
					"is similar to the one presented two trials back (-2).</b></p>" +
					"<br />" +
					"<p align=\"center\">Here is an example:</p></html>");
			
			expression.put("nBackExplanationHeader3Back", "<html><p align=\"center\"><b>Your task is to determine if the symbol you see  " +
					"is similar to the one present three trials back (-3).</b></p>" +
					"<br />" +
					"<p align=\"center\">Here is an example:</p></html>");*/
			
			expression.put("nBackExplanationHeader0Back", "<html><p align=\"center\">Your task is to determine if the symbol you see " +
					"is a 3." +
					"<br />" +
					"If you see a 3, press the <b>%key1%</b> key. Otherwise press the <b>%key2%</b> key.</p></html>");
	
			
			expression.put("nBackExplanationHeader1Back", "<html><p align=\"center\">Your task is to determine if the symbol you see  " +
					"is similar to the preceding one (-1)." +
					"<br />" +
					"If both symbols are similar (they might vary slightly), press the <b>%key1%</b> key. If not, press the <b>%key2%</b> key.</p></html>");
			
			expression.put("nBackExplanationHeader2Back", "<html><p align=\"center\">Your task is to determine if the symbol you see  " +
					"is similar to the one presented two trials back (-2)." +
					"<br />" +
					"If both symbols are similar (they might vary slightly), press the <b>%key1%</b> key. If not, press the <b>%key2%</b> key.</p></html>");
			
			expression.put("nBackExplanationHeader3Back", "<html><p align=\"center\">Your task is to determine if the symbol you see  " +
					"is similar to the one present three trials back (-3)." +
					"<br />" +
					"If both symbols are similar (they might vary slightly), press the <b>%key1%</b> key. If not, press the <b>%key2%</b> key.</p></html>");			
			
			expression.put("allStimuliNbackSPHeader", "<html><p align=\"center\">If both symbols are similar (they might vary slightly), press the <b>%key1%</b> key. If not, press the <b>%key2%</b> key.</p></html>");
			
			expression.put("nBackAsteriskFooter1Back", "<html><p align=\"center\">The \"+\" symbol indicates that the trial has ended<br />" +
					"and that you can no longer respond.</p>" +
					"<br />" + 
					"<p align=\"center\">Also, you do not have to answer to the first <br />" +
					"symbol since there was no symbol<br />" +
					"preceding it.</p>" +
					"<br />" +
					"<p align=\"center\">Therefore, you must keep the first symbol in mind in order to answer later. </html>");
			
			expression.put("nBackAsteriskFooter2Back", "<html><p align=\"center\">The  \"+\" symbol indicates that the trial has ended<br />" +
					"and that you can no longer respond.</p>" +
					"<br />" + 
					"<p align=\"center\">Also, you do not have to answer to the first two <br />" +
					"symbols since there were no symbols<br />" +
					"preceding them.</p>" +
					"<br />" +
					"<p align=\"center\">Therefore, you only have to keep the first two symbols in mind in order to answer later. </html>");		
			
			expression.put("nBackAsteriskFooter3Back", "<html><p align=\"center\">The  \"+\" symbol indicates that the trial has ended<br />" +
					"and that you can no longer respond.</p>" +
					"<br />" + 
					"<p align=\"center\">Also, you not have to answer the first three <br />" +
					"symbols since there were no symbols<br />" +
					"preceding them.</p>" +
					"<br />" +
					"<p align=\"center\">Therefore, you only have to keep three first symbols im mind in order to answer later. </html>");	
			}
	}
	


	public static void setPriority (String priority)
	{
			if (priority == "left")
			{
				expression.put("prioriteHeader", "<html><p align=\"center\">D'après vos performances recueillies au dernier bloc :<br />" +
						"Vous accordez <b>trop</b> d'attention à la tâche de <b>gauche</b>.Pour le prochain bloc, portez davantage attention à la tâche de <b>droite et tentez d'être plus rapide avec cette main</b>");
				expression.put("prioriteFooter", "<html><p align=\"center\">N'oubliez pas, les deux tâches sont <b>aussi importantes</b> une que l'autre<br />" +
						"<b> Bonne chance! </b>");
			}
			else if (priority == "right")
			{
				expression.put("prioriteHeader", "<html><p align=\"center\">D'après vos performances recueillies au dernier bloc :<br />" +
						"Vous accordez <b>trop</b> d'attention à la tâche de <b>droite</b>.Pour le prochain bloc, portez davantage attention à la tâche de <b>gauche et tentez d'être plus rapide avec cette main</b>");
				expression.put("prioriteFooter", "<html><p align=\"center\">N'oubliez pas, les deux tâches sont <b>aussi importantes</b> une que l'autre<br />" +
						"<b> Bonne chance! </b>");
			}
			else if (priority == "equal")
			{
				expression.put("prioriteHeader", "<html><p align=\"center\">D'après vos performances recueillies au dernier bloc :<br />" +
						"Vous accordez <b>autant d'importance aux deux tâches à effectuer </b>");
				expression.put("prioriteFooter", "<html><p align=\"center\">C'est exactement ce que nous attendons de vous! <br />" +
						"<b>Bravo et continuez sur cette voie!! </b>");
			}	
	}
	
	public String writeArray(Stimulus[] el, String lastSeparator, boolean isFullName){
		String output = "";
		int arrayLength = el.length;
		
		for(int i = 0; i < arrayLength; i++){
			if(isFullName)
				output += el[i].getFullName();
			else
				output += el[i].getName();
			if(i == arrayLength - 2){
				output += " " + lastSeparator + " ";
			}else if (i < arrayLength - 1){
				output += ", ";
			}
		}
		return output;
	}

}
