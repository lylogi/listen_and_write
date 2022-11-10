package com.example.listen_and_write.modules.scripts;

import com.example.listen_and_write.framework.BaseScript;

import de.halfbit.tinymachine.TinyMachine;

public class BlankModeScript extends BaseScript {
    @Override
    public void init() { }

    public BlankModeScript() {
        super();
        super.tinyMachine = new TinyMachine(new ScriptHandlerImp(this), ScriptHandlerImp.PEDING);
    }
    public class ScriptHandlerImp extends BaseScriptHandler {

        public ScriptHandlerImp(BaseScript _script) {
            super(_script);
        }
    }
}
