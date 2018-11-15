package id.co.ultrajaya.hrdinspection.clsumum;

/**
 * Created by it-aris on 07/09/2016.
 */

import java.util.ArrayList;

/**
 * Created by Aris on 12/8/2014.
 */
public class mvitem {

    public int DCount(Integer AttributeNumber )
    {
        int aval = 0;
        String[] bstr;
        String adata = GetData(AttributeNumber);
        bstr = adata.split(Character.toString(VM));
        return bstr.length;

    }
    public static int DCount(String iData, String iPemisah)
    {
        int aval = 0;
        String[] bstr;
        if (iPemisah == Character.toString(AM))
        {
            bstr = iData.split(Character.toString(AM));
            aval =  bstr.length;
        }
        if (iPemisah == Character.toString(VM))
        {
            bstr = iData.split(Character.toString(VM));
            aval = bstr.length;
        }
        return aval;
    }

    public static final char AM =  254;
    public static final char VM =  253;

    ArrayList<String> AllList = new ArrayList<String>();

    public void SetData (String value)
    {
        String[] bstr;
        String[] vstr;
        String astr, cstr, dstr;
        ArrayList<String> alist = new ArrayList<String>();
        astr = value ;
        bstr = astr.split(Character.toString(AM));
        for(int i = 0; i < bstr.length; i++)
        {
            cstr = bstr[i];
            if (cstr.contains(Character.toString(VM)))
            {
                vstr = cstr.split(Character.toString(VM));
                for(int j = 0; j < vstr.length;j++)
                {
                    dstr= vstr[j];
                    SetData(i+1,j+1, dstr);
                }
            }
            else
            {
                SetData(i+1, cstr);
            }
        }
    }

    public String GetData(int AttributeNumber)
    {

        if (AllList.toArray().length >= AttributeNumber) {
            return AllList.get(AttributeNumber - 1);
        } else {
            return "";
        }


    }
    public void SetData (int AttributeNumber,String value)
    {
        if (AllList.toArray().length < AttributeNumber)
        {
            int aidx, bidx;
            for (aidx = AllList.size(); aidx < AttributeNumber; aidx++)
            {
                AllList.add("");
            }

        }
        String astr = AllList.get(AttributeNumber - 1);
        astr = value ;
        AllList.set(AttributeNumber - 1,  astr);

    }
    public String GetData(int AttributeNumber, int ValueNumber)
    {

        String astr,cstr;
        String[] bstr;
        ArrayList<String> alist = new ArrayList<String>();
        astr = GetData(AttributeNumber) ;
        bstr = astr.split(Character.toString(VM));
        Integer zidx;
        for(int i = 0; i < bstr.length; i++)
        {
            cstr = bstr[i];
            alist.add(cstr);
        }


        //  foreach (String cstr in bstr)
        //  {
        //      alist.Add(cstr);
        //  }
        if (alist.size() >= ValueNumber)
        {
            return alist.get(ValueNumber - 1);
        }
        else
        {
            return "";
        }
    }
    public void SetData (int AttributeNumber,int ValueNumber,String value)
    {

        /**  if (AllList.toArray().length < AttributeNumber)
         {
         int aidx, bidx;
         for (aidx = AllList.size(); aidx < AttributeNumber; aidx++)
         {
         AllList.add("");
         }

         }
         String astr = AllList.get(AttributeNumber - 1);
         astr = value ;
         AllList.set(AttributeNumber - 1,  astr);
         */

        String astr, eStr,cstr ;
        String[] bstr;
        ArrayList<String> alist = new ArrayList<String>();
        astr = GetData(AttributeNumber) ;
        bstr = astr.split(Character.toString(VM));
        for(int i = 0; i < bstr.length; i++)
        {
            cstr = bstr[i];
            alist.add(cstr);
        }

        if (alist.size() < ValueNumber)
        {
            int aidx, bidx;
            for (aidx = alist.size(); aidx < ValueNumber; aidx++)
            {
                alist.add("");
            }

        }
        if (AllList.size() < AttributeNumber)
        {
            int aidx, bidx;
            for (aidx = AllList.size(); aidx < AttributeNumber; aidx++)
            {
                AllList.add("");
            }

        }
        String dstr = alist.get(ValueNumber - 1);
        dstr = value ;
        String fStr;
        alist.set(ValueNumber  - 1, dstr);
        eStr = null;
        for(int i = 0; i < alist.size(); i++)
        {
            fStr = alist.get(i);
            if (eStr == null)
            {
                eStr = fStr;
            }
            else
            {
                eStr = eStr + Character.toString(VM) + fStr;
            }
        }

           /* foreach (String fStr in alist)
            {
                if (eStr == null)
                {
                    eStr = fStr;
                }
                else
                {
                    eStr = eStr + VM + fStr;
                }
            }
            */

        AllList.set(AttributeNumber - 1,  eStr);
        //this[AttributeNumber] = eStr;
    }




    public String Contents()
    {
        String ahasil;
        String fStr;
        ahasil = null;
        for(int i = 0; i < AllList.size(); i++)
        {
            fStr = AllList.get(i);
            if (ahasil == null)
            {
                ahasil = fStr;
            }
            else
            {
                ahasil = ahasil +  Character.toString(AM)  + fStr;
            }
        }

           /* foreach (String fStr in AllList)
            {
                if (ahasil == null)
                {
                    ahasil = fStr;
                }
                else
                {
                    ahasil = ahasil +  AM  + fStr;
                }
            }

            */
        return ahasil;
    }
       /* public object this[int AttributeNumber, int ValueNumber, int SubvalueNumber, string ConversionCode]
        {
            get
            {
                return this[AttributeNumber, ValueNumber, 0, ""];
            }
            set
            {
                this[AttributeNumber, ValueNumber, 0, ""] = value;
            }

        }
        */

}

