package org.openstreetmap.josm.plugins.turnlanestagging;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.SelectionChangedListener;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;
import org.openstreetmap.josm.tools.Shortcut;

/**
 *
 * @author ruben
 */
public class LaunchAction extends JosmAction implements SelectionChangedListener {

    public LaunchAction() {
        super(tr("Edit turn lanes tags"),
                "turnlanes-tagging",
                tr("Turn lanes tagging - Editor"),
                Shortcut.registerShortcut("edit:launchturnlanestageditor ",
                        tr("Tool: {0}", tr("Launches turn lane tag editor dialog")),
                        KeyEvent.VK_2, Shortcut.ALT_SHIFT),
                true);
        DataSet.addSelectionListener(this);
        setEnabled(false);
      //  AddTagAction addTagAction = new AddTagAction();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        launchEditor();
    }

    @Override
    public void selectionChanged(Collection<? extends OsmPrimitive> newSelection) {
        setEnabled(newSelection != null && newSelection.size() > 0 && isRoad());
    }

    protected void launchEditor() {
        if (!isEnabled()) {
            return;
        }
        TagEditorDialog dialog = TagEditorDialog.getInstance();
        dialog.startEditSession();
        dialog.setVisible(true);
    }

    public boolean isRoad() {
        Collection<OsmPrimitive> selection = Main.main.getCurrentDataSet().getSelected();
        for (OsmPrimitive element : selection) {
            for (String key : element.keySet()) {
                if (key.equals("highway")) {
                    return true;
                }
            }
        }
        return false;
    }
}
