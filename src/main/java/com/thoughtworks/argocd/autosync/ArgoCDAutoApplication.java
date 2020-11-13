package com.thoughtworks.argocd.autosync;

import com.thoughtworks.argocd.autosync.domain.ParaObject;
import com.thoughtworks.argocd.autosync.utils.ParseParasUtil;
import com.thoughtworks.argocd.autosync.client.BranchClient;

public class ArgoCDAutoApplication {

    private static final String defaultBaseBranch = "master";

    public static void main(String[] args) {

        printLogo();

        ParaObject paraObject = ParseParasUtil.getParaObject(args, defaultBaseBranch);
        BranchClient branchClient = new BranchClient(paraObject);

        try {
            branchClient.updateDeploymentTag();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("------------------finish------------------------\n");

    }


    private static void printLogo() {
        System.out.println(getLogo());
    }

    /**
     * http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20
     */
    private static String getLogo() {

        return "\n                                      .___                       __                                                \n" +
                "_____ _______  ____   ____   ____   __| _/        _____   __ ___/  |_  ____             _________.__. ____   ____  \n" +
                "\\__  \\\\_  __ \\/ ___\\ /  _ \\_/ ___\\ / __ |  ______ \\__  \\ |  |  \\   __\\/  _ \\   ______  /  ___<   |  |/    \\_/ ___\\ \n" +
                " / __ \\|  | \\/ /_/  >  <_> )  \\___/ /_/ | /_____/  / __ \\|  |  /|  | (  <_> ) /_____/  \\___ \\ \\___  |   |  \\  \\___ \n" +
                "(____  /__|  \\___  / \\____/ \\___  >____ |         (____  /____/ |__|  \\____/          /____  >/ ____|___|  /\\___  >\n" +
                "     \\/     /_____/             \\/     \\/              \\/                                  \\/ \\/         \\/     \\/ \n\n"

                ;
    }

}
