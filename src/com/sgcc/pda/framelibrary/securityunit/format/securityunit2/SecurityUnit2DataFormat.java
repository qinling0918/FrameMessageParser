package com.sgcc.pda.framelibrary.securityunit.format.securityunit2;

import com.sgcc.pda.framelibrary.securityunit.SecurityUnitFrame;

public class SecurityUnit2DataFormat extends DataFormat {
    public SecurityUnit2DataFormat(SecurityUnitFrame.Parser parser) {
        super(parser);
    }
    public SecurityUnit2DataFormat(SecurityUnitFrame securityUnit2Frame) {
        super(new SecurityUnitFrame.Parser(securityUnit2Frame.toHexString()));
    }
    public SecurityUnit2DataFormat(String securityUnit2FrameStr) {
        super(new SecurityUnitFrame.Parser(securityUnit2FrameStr));
    }
    @Override
    public String getMainSignInstructions() {
        return null;
    }

    @Override
    public String getDataFormat() {
        return null;
    }

    @Override
    public String getCommandResponseCodeInstructions() {
        return null;
    }

    @Override
    public String getStatusCodeInstructions() {
        return null;
    }
}
