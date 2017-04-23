package com.ksgagro.gps.controller.JSON;

import com.ksgagro.gps.domain.MapObjectField;
import com.ksgagro.gps.domain.MapObjectFieldType;
import com.ksgagro.gps.domain.TestPay;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kma on 03.12.2016.
 */
@Component
public class Mapper {
    public MapObjectJSON toJSON(MapObjectField src) {
        MapObjectJSON ret = new MapObjectJSON();
        ret.setColor("#C9A646");
        ret.setFieldNumber(src.getFieldNumber());
        ret.setId(src.getId());
        ret.setLatLngArray(src.getLatLngArray());
        return ret;
    }

    public List<MapObjectJSON> toJSONList(List<MapObjectField> src) {
        List<MapObjectJSON> ret = new ArrayList<>();
        for (MapObjectField field : src) {
            ret.add(toJSON(field));
        }
        return ret;
    }

    public MapObjectJSON toJSON(TestPay src) {
        MapObjectJSON ret = new MapObjectJSON();
        ret.setLatLngArray(src.getCoordinateses());
        ret.setColor("#FF0000");
        ret.setId(src.getId().intValue());
        ret.setFieldNumber(src.getKadastrNumber());
        return ret;
    }
    public List<MapObjectJSON> toJSONTestPayList(List<TestPay> src) {
        List<MapObjectJSON> ret = new ArrayList<>();
        for (TestPay field : src) {
            ret.add(toJSON(field));
        }
        return ret;
    }

    public List<MapObjectFieldTypeJson> toTypesJSONs(List<MapObjectFieldType> types) {
        List<MapObjectFieldTypeJson> ret = new ArrayList<>();
        for(MapObjectFieldType type: types)
            ret.add(toType(type));
        return ret;
    }

    public MapObjectFieldTypeJson toType(MapObjectFieldType entity){
        MapObjectFieldTypeJson ret = new MapObjectFieldTypeJson();
        ret.setId(entity.getId());
        ret.setName(entity.getName());
        return ret;
    }
}
