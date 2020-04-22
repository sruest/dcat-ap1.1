//=============================================================================
//===	Copyright (C) 2001-2013 Food and Agriculture Organization of the
//===	United Nations (FAO-UN), United Nations World Food Programme (WFP)
//===	and United Nations Environment Programme (UNEP)
//===
//===	This program is free software; you can redistribute it and/or modify
//===	it under the terms of the GNU General Public License as published by
//===	the Free Software Foundation; either version 2 of the License, or (at
//===	your option) any later version.
//===
//===	This program is distributed in the hope that it will be useful, but
//===	WITHOUT ANY WARRANTY; without even the implied warranty of
//===	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//===	General Public License for more details.
//===
//===	You should have received a copy of the GNU General Public License
//===	along with this program; if not, write to the Free Software
//===	Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
//===
//===	Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
//===	Rome - Italy. email: geonetwork@osgeo.org
//==============================================================================

package org.fao.geonet.kernel.harvest.harvester.dcatap;

import org.fao.geonet.Logger;
import org.fao.geonet.kernel.harvest.harvester.AbstractHarvester;
import org.fao.geonet.kernel.harvest.harvester.HarvestError;
import org.fao.geonet.kernel.harvest.harvester.HarvestResult;

import java.sql.SQLException;
import java.util.List;

//=============================================================================

public class DCATAPHarvester extends AbstractHarvester<HarvestResult, DCATAPParams> {

    //--------------------------------------------------------------------------
    //---
    //--- Init
    //---
    //--------------------------------------------------------------------------

    private List<HarvestError> errors;


    //---------------------------------------------------------------------------
    //---
    //--- Harvest
    //---
    //---------------------------------------------------------------------------

    protected void storeNodeExtra(DCATAPParams p, String path,
                                  String siteId, String optionsId) throws SQLException {
        DCATAPParams params = (DCATAPParams) p;

        harvesterSettingsManager.add("id:" + siteId, "baseUrl", params.baseUrl);
        harvesterSettingsManager.add("id:" + siteId, "icon", params.icon);
        harvesterSettingsManager.add("id:" + siteId, "rdfSyntax", params.rdfSyntax);

        //--- store search nodes

//        for (Search s : params.getSearches()) {
//            String searchID = harvesterSettingsManager.add(path, "search", "");
//
//            harvesterSettingsManager.add("id:" + searchID, "freeText", s.freeText);
//        }
    }

    @Override
    protected DCATAPParams createParams() {
        return new DCATAPParams(dataMan);
    }


    //---------------------------------------------------------------------------
    //---
    //--- Variables
    //---
    //---------------------------------------------------------------------------

    public void doHarvest(Logger log) throws Exception {
        Harvester h = new Harvester(cancelMonitor, log, context, params);
        result = h.harvest(log);
        errors = h.getErrors();
    }

    @Override
    public List<HarvestError> getErrors() {
        return errors;
    }
}
