package com.smartdatachain.api.web.po;

import java.util.List;

public class DappDataListPO {

    private int count;
    private List<DappDataPO> dappDataPOS;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DappDataPO> getDappDataPOS() {
        return dappDataPOS;
    }

    public void setDappDataPOS(List<DappDataPO> dappDataPOS) {
        this.dappDataPOS = dappDataPOS;
    }
}
