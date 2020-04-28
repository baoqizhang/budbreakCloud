package com.budbreak.pan.controller.assembler.hibernate;

import com.budbreak.pan.controller.command.hibernate.SequenceCreateCommand;
import com.budbreak.pan.controller.command.hibernate.SequenceUpdateCommand;
import com.budbreak.pan.entity.hibernate.Sequence;

import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
public class SequenceAssembler{
    public static Sequence toSequence(SequenceCreateCommand command){
        Date now = new Date();

        return new Sequence()
            .setVal(command.getVal());
    }

    public static Sequence toSequence(SequenceUpdateCommand command){
        Date now = new Date();

        return new Sequence()
            .setVal(command.getVal());
    }
}
