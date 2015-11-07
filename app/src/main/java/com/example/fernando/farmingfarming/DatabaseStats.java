package com.example.fernando.farmingfarming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando on 9/16/15.
 * <p/>
 * create the best
 */
public class DatabaseStats extends SQLiteOpenHelper {

    // version of sqlite
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "dataManager";

    // table name
    private static final String TABLE_STATS = "stats";

    // stats table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CROP = "crop";
    private static final String KEY_REGION = "region";
    private static final String KEY_PRIMARY_RETURNS = "primary_returns";
    private static final String KEY_SECONDARY_RETURNS = "secondary_returns";
    private static final String KEY_TOTAL_RETURNS = "total_returns";
    private static final String KEY_SEED = "seed";
    private static final String KEY_FERTILIZER = "fertilizer";
    private static final String KEY_CHEMICALS = "chemicals";
    private static final String KEY_CUSTOM_OPERATIONS = "custom_ops";
    private static final String KEY_FUEL_LUBE_ELECTRICITY = "fuel_lube_elec";
    private static final String KEY_REPAIRS = "repairs";
    private static final String KEY_IRRIGATION_WATER = "irrigation_water";
    private static final String KEY_INTEREST_ON_CAP = "int_on_cap";
    private static final String KEY_TOTAL_OPERATIONAL_COSTS = "total_op_costs";
    private static final String KEY_HIRED_LABOR = "hired_labor";
    private static final String KEY_COST_UNPAID_LABOR = "unpaid_labor";
    private static final String KEY_CAP_RECOVERY_OF_EQUIPMENT = "cap_recovery_of_equip";
    private static final String KEY_COST_LAND_AND_RENTAL_RATES = "land_rental_rates";
    private static final String KEY_TAXES_AND_INSURANCE = "taxes_insurance";
    private static final String KEY_MISCELLANEOUS = "miscellaneous";
    private static final String KEY_GENERAL_FARM_OVERHEAD = "general_farm_overhead";
    private static final String KEY_TOTAL_OVERHEAD_COST = "total_overhead_costs";
    private static final String KEY_TOTAL_COST = "total_costs";
    private static final String KEY_YIELD = "yield";
    private static final String KEY_PRICE = "price";


    private static DatabaseStats sInstance;

    //not sure if this last size of acres planted really does anything
    private static final String KEY_SIZE_ACRES_PLANTED = "size_acres_planted";

    // constructor, still not quite sure what this is for
    public DatabaseStats(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static synchronized DatabaseStats getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseStats(context.getApplicationContext());
        }
        return sInstance;
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STATS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_CROP + " TEXT,"
                + KEY_REGION + " TEXT, "
                + KEY_PRIMARY_RETURNS + " REAL, "
                + KEY_SECONDARY_RETURNS + " REAL, "
                + KEY_TOTAL_RETURNS + " REAL, "
                + KEY_SEED + " REAL, "
                + KEY_FERTILIZER + " REAL, "
                + KEY_CHEMICALS + " REAL, "
                + KEY_CUSTOM_OPERATIONS + " REAL, "
                + KEY_FUEL_LUBE_ELECTRICITY + " REAL, "
                + KEY_REPAIRS + " REAL, "
                + KEY_IRRIGATION_WATER + " REAL, "
                + KEY_INTEREST_ON_CAP + " REAL, "
                + KEY_TOTAL_OPERATIONAL_COSTS + " REAL, "
                + KEY_HIRED_LABOR + " REAL, "
                + KEY_COST_UNPAID_LABOR + " REAL, "
                + KEY_CAP_RECOVERY_OF_EQUIPMENT + " REAL, "
                + KEY_COST_LAND_AND_RENTAL_RATES + " REAL, "
                + KEY_TAXES_AND_INSURANCE + " REAL, "
                + KEY_MISCELLANEOUS + " REAL, "
                + KEY_GENERAL_FARM_OVERHEAD + " REAL, "
                + KEY_TOTAL_OVERHEAD_COST + " REAL, "
                + KEY_TOTAL_COST + " REAL, "
                + KEY_YIELD + " INTEGER, "
                + KEY_PRICE + " REAL, "
                + KEY_SIZE_ACRES_PLANTED + " INTEGER"
                + ")";

        db.execSQL(CREATE_STATS_TABLE);

    }

    //upgrading the database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //TODO: add saving of rows that are already saved so that you don't lose the user data
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);

        // Create tables again
        onCreate(db);

    }


    // this method adds another crop to the database
    public void addCropStats(CropStats crop) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, crop.getTitle());
        values.put(KEY_CROP, crop.getCropName());
        values.put(KEY_REGION, crop.getRegionName());
        values.put(KEY_PRIMARY_RETURNS, crop.getPrimaryReturns());
        values.put(KEY_SECONDARY_RETURNS, crop.getSecondaryReturns());
        values.put(KEY_TOTAL_RETURNS, crop.getTotalReturns());
        values.put(KEY_SEED, crop.getSeed());
        values.put(KEY_FERTILIZER, crop.getFertilizer());
        values.put(KEY_CHEMICALS, crop.getChemicals());
        values.put(KEY_CUSTOM_OPERATIONS, crop.getCustomOps());
        values.put(KEY_FUEL_LUBE_ELECTRICITY, crop.getFueLubeElec());
        values.put(KEY_REPAIRS, crop.getRepairs());
        values.put(KEY_IRRIGATION_WATER, crop.getIrrigationWater());
        values.put(KEY_INTEREST_ON_CAP, crop.getIntOnCap());
        values.put(KEY_TOTAL_OPERATIONAL_COSTS, crop.getTotalOperationalCosts());
        values.put(KEY_HIRED_LABOR, crop.getHiredLabor());
        values.put(KEY_COST_UNPAID_LABOR, crop.getCostUnpaidLabor());
        values.put(KEY_CAP_RECOVERY_OF_EQUIPMENT, crop.getCapRecoveryOfEquip());
        values.put(KEY_COST_LAND_AND_RENTAL_RATES, crop.getCostOfLandRentalRate());
        values.put(KEY_TAXES_AND_INSURANCE, crop.getTaxesInsurance());
        values.put(KEY_MISCELLANEOUS, crop.getMiscellaneous());
        values.put(KEY_GENERAL_FARM_OVERHEAD, crop.getGeneralFarmOverhead());
        values.put(KEY_TOTAL_OVERHEAD_COST, crop.getTotalOverheadCost());
        values.put(KEY_TOTAL_COST, crop.getTotalCost());
        values.put(KEY_YIELD, crop.getYield());
        values.put(KEY_PRICE, crop.getPrice());
        values.put(KEY_SIZE_ACRES_PLANTED, crop.getSizeAcresPlanted());


        db.insert(TABLE_STATS, null, values);


    }


    // Getting single crop stats
    // only need the id because only the saved data points will be on this database
    // this means that, when clicking on the dataset that the user wants to see,
    // the dataset will be linked via the id on the "saved state" activity
    public CropStats getCrop(String title) {

        CropStats crop;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STATS, new String[]{KEY_ID, KEY_CROP, KEY_REGION,
                KEY_PRIMARY_RETURNS, KEY_SECONDARY_RETURNS, KEY_TOTAL_RETURNS,
                KEY_SEED, KEY_FERTILIZER, KEY_CHEMICALS, KEY_CUSTOM_OPERATIONS,
                KEY_FUEL_LUBE_ELECTRICITY, KEY_REPAIRS, KEY_IRRIGATION_WATER,
                KEY_INTEREST_ON_CAP, KEY_TOTAL_OPERATIONAL_COSTS, KEY_HIRED_LABOR,
                KEY_COST_UNPAID_LABOR, KEY_CAP_RECOVERY_OF_EQUIPMENT, KEY_COST_LAND_AND_RENTAL_RATES,
                KEY_TAXES_AND_INSURANCE, KEY_MISCELLANEOUS, KEY_GENERAL_FARM_OVERHEAD, KEY_TOTAL_OVERHEAD_COST,
                KEY_TOTAL_COST, KEY_YIELD, KEY_PRICE, KEY_SIZE_ACRES_PLANTED
        }, "KEY_TITLE like" + title, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();


            //set all values for the updated crop stats!
            crop = new CropStats();

            crop.setId(Integer.parseInt(cursor.getString(0)));
            crop.setTitle(cursor.getString(1));
            crop.setCropName(cursor.getString(2));
            crop.setRegionName(cursor.getString(3));
            crop.setPrimaryReturns(Float.parseFloat(cursor.getString(4)));
            crop.setSecondaryReturns(Float.parseFloat(cursor.getString(5)));
            //crop.setTotalReturns(Float.parseFloat(cursor.getString(6)));
            crop.setSeed(Float.parseFloat(cursor.getString(7)));
            crop.setFertilizer(Float.parseFloat(cursor.getString(8)));
            crop.setChemicals(Float.parseFloat(cursor.getString(9)));
            crop.setCustomOps(Float.parseFloat(cursor.getString(10)));
            crop.setFueLubeElec(Float.parseFloat(cursor.getString(11)));
            crop.setRepairs(Float.parseFloat(cursor.getString(12)));
            crop.setIrrigationWater(Float.parseFloat(cursor.getString(13)));
            crop.setIntOnCap(Float.parseFloat(cursor.getString(14)));
            //crop.setTotalOperationalCosts(Float.parseFloat(cursor.getString(15)));
            crop.setHiredLabor(Float.parseFloat(cursor.getString(16)));
            crop.setCostUnpaidLabor(Float.parseFloat(cursor.getString(17)));
            crop.setCapRecoveryOfEquip(Float.parseFloat(cursor.getString(18)));
            crop.setCostOfLandRentalRate(Float.parseFloat(cursor.getString(19)));
            crop.setTaxesInsurance(Float.parseFloat(cursor.getString(20)));
            crop.setMiscellaneous(Float.parseFloat(cursor.getString(21)));
            crop.setGeneralFarmOverhead(Float.parseFloat(cursor.getString(22)));
            //crop.setTotalOverheadCost(Float.parseFloat(cursor.getString(23)));
            //crop.setTotalCost(Float.parseFloat(cursor.getString(24)));
            crop.setYield(Integer.parseInt(cursor.getString(25)));
            crop.setPrice(Float.parseFloat(cursor.getString(26)));
            crop.setSizeAcresPlanted(Integer.parseInt(cursor.getString(27)));

        } else {
            crop = null;
        }

        return crop;

    }


    // Getting cropstats count
    public int getCropStatsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STATS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    // Updating single cropstat
    public int updateCrop(CropStats crop, String title) {

        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("updatecrop db", String.valueOf(crop.getTitle()));
        Log.d("yield db", crop.getYield() + "");
        Log.d("acres db", crop.getSizeAcresPlanted() + "");

        ContentValues values = new ContentValues();
        values.put(KEY_CROP, crop.getCropName());
        values.put(KEY_REGION, crop.getRegionName());
        values.put(KEY_TITLE, title);
        values.put(KEY_PRIMARY_RETURNS, crop.getPrimaryReturns());
        values.put(KEY_SECONDARY_RETURNS, crop.getSecondaryReturns());
        values.put(KEY_TOTAL_RETURNS, crop.getTotalReturns());
        values.put(KEY_SEED, crop.getSeed());
        values.put(KEY_FERTILIZER, crop.getFertilizer());
        values.put(KEY_CHEMICALS, crop.getChemicals());
        values.put(KEY_CUSTOM_OPERATIONS, crop.getCustomOps());
        values.put(KEY_FUEL_LUBE_ELECTRICITY, crop.getFueLubeElec());
        values.put(KEY_REPAIRS, crop.getRepairs());
        values.put(KEY_IRRIGATION_WATER, crop.getIrrigationWater());
        values.put(KEY_INTEREST_ON_CAP, crop.getIntOnCap());
        values.put(KEY_TOTAL_OPERATIONAL_COSTS, crop.getTotalOperationalCosts());
        values.put(KEY_HIRED_LABOR, crop.getHiredLabor());
        values.put(KEY_COST_UNPAID_LABOR, crop.getCostUnpaidLabor());
        values.put(KEY_CAP_RECOVERY_OF_EQUIPMENT, crop.getCapRecoveryOfEquip());
        values.put(KEY_COST_LAND_AND_RENTAL_RATES, crop.getCostOfLandRentalRate());
        values.put(KEY_TAXES_AND_INSURANCE, crop.getTaxesInsurance());
        values.put(KEY_MISCELLANEOUS, crop.getMiscellaneous());
        values.put(KEY_GENERAL_FARM_OVERHEAD, crop.getGeneralFarmOverhead());
        values.put(KEY_TOTAL_OVERHEAD_COST, crop.getTotalOverheadCost());
        values.put(KEY_TOTAL_COST, crop.getTotalCost());
        values.put(KEY_YIELD, crop.getYield());
        values.put(KEY_PRICE, crop.getPrice());
        values.put(KEY_SIZE_ACRES_PLANTED, crop.getSizeAcresPlanted());


        return db.update(TABLE_STATS, values, KEY_TITLE + " = ?", new String[]{title});


    }


    // Deleting single cropstat
    public void deleteCropStat(CropStats crop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STATS, KEY_ID + " = ?", new String[]{
                String.valueOf(crop.getId())
        });


    }

    // Deleting single contact
    // Only used for debugging purposes
    public void deleteAllCrops() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STATS, null, null);


    }

    public ArrayList<CropStats> getCropRegionCropStats(String crop, String region) {

        SQLiteDatabase db = this.getReadableDatabase();


        String whereClause = KEY_CROP + " = ? AND " + KEY_REGION + "= ? ";
        String[] whereArgs = new String[]{crop, region};
        String orderBy = KEY_TITLE;
        Log.d("db crop name", crop);


        ArrayList<CropStats> list = new ArrayList<>();


        Cursor cursor = db.query(TABLE_STATS, new String[]{KEY_ID, KEY_TITLE, KEY_CROP, KEY_REGION,
                KEY_PRIMARY_RETURNS, KEY_SECONDARY_RETURNS, KEY_TOTAL_RETURNS,
                KEY_SEED, KEY_FERTILIZER, KEY_CHEMICALS, KEY_CUSTOM_OPERATIONS,
                KEY_FUEL_LUBE_ELECTRICITY, KEY_REPAIRS, KEY_IRRIGATION_WATER,
                KEY_INTEREST_ON_CAP, KEY_TOTAL_OPERATIONAL_COSTS, KEY_HIRED_LABOR,
                KEY_COST_UNPAID_LABOR, KEY_CAP_RECOVERY_OF_EQUIPMENT, KEY_COST_LAND_AND_RENTAL_RATES,
                KEY_TAXES_AND_INSURANCE, KEY_MISCELLANEOUS, KEY_GENERAL_FARM_OVERHEAD, KEY_TOTAL_OVERHEAD_COST,
                KEY_TOTAL_COST, KEY_YIELD, KEY_PRICE, KEY_SIZE_ACRES_PLANTED
        }, whereClause, whereArgs, null, null, orderBy);

        Log.d("movetofirst", "" + cursor.moveToFirst());
        if (cursor != null) {


            while (!cursor.isAfterLast()) {
                CropStats corn = new CropStats();
                corn.setId(Integer.parseInt(cursor.getString(0)));
                corn.setTitle(cursor.getString(1));
                corn.setCropName(cursor.getString(2));
                corn.setRegionName(cursor.getString(3));
                corn.setPrimaryReturns(Float.parseFloat(cursor.getString(4)));
                corn.setSecondaryReturns(Float.parseFloat(cursor.getString(5)));
                //corn.setTotalReturns(Float.parseFloat(cursor.getString(6)));
                corn.setSeed(Float.parseFloat(cursor.getString(7)));
                corn.setFertilizer(Float.parseFloat(cursor.getString(8)));
                corn.setChemicals(Float.parseFloat(cursor.getString(9)));
                corn.setCustomOps(Float.parseFloat(cursor.getString(10)));
                corn.setFueLubeElec(Float.parseFloat(cursor.getString(11)));
                corn.setRepairs(Float.parseFloat(cursor.getString(12)));
                corn.setIrrigationWater(Float.parseFloat(cursor.getString(13)));
                corn.setIntOnCap(Float.parseFloat(cursor.getString(14)));
                //corn.setTotalOperationalCosts(Float.parseFloat(cursor.getString(15)));
                corn.setHiredLabor(Float.parseFloat(cursor.getString(16)));
                corn.setCostUnpaidLabor(Float.parseFloat(cursor.getString(17)));
                corn.setCapRecoveryOfEquip(Float.parseFloat(cursor.getString(18)));
                corn.setCostOfLandRentalRate(Float.parseFloat(cursor.getString(19)));
                corn.setTaxesInsurance(Float.parseFloat(cursor.getString(20)));
                corn.setMiscellaneous(Float.parseFloat(cursor.getString(21)));
                corn.setGeneralFarmOverhead(Float.parseFloat(cursor.getString(22)));
                //corn.setTotalOverheadCost(Float.parseFloat(cursor.getString(23)));
                //corn.setTotalCost(Float.parseFloat(cursor.getString(24)));
                corn.setYield(Integer.parseInt(cursor.getString(25)));
                corn.setPrice(Float.parseFloat(cursor.getString(26)));
                corn.setSizeAcresPlanted(Integer.parseInt(cursor.getString(27)));
                list.add(corn);
                cursor.moveToNext();

            }

        } else {
            Log.d("cursor null", "true");


        }


        return list;

    }


}
