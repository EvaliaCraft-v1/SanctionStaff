package com.elikill58.sanction.spigot.hook;

import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.JDA;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.objects.MessageFormat;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.MessageUtil;
import github.scarsz.discordsrv.util.PlaceholderUtil;
import github.scarsz.discordsrv.util.TimeUtil;

public class DiscordSRVHook {

	public static void sendReportMessage(Player player, Player reported, String reason) {
		JDA jda = DiscordSRV.getPlugin().getJda();
		MessageFormat messageFormat = DiscordSRV.getPlugin().getMessageFromConfiguration("ReportMessage");
		if (messageFormat == null)
			return;

		String avatarUrl = DiscordSRV.getAvatarUrl(player);
		String botAvatarUrl = jda.getSelfUser().getEffectiveAvatarUrl();
		String botName = DiscordSRV.getPlugin().getMainGuild() != null ? DiscordSRV.getPlugin().getMainGuild().getSelfMember().getEffectiveName() : jda.getSelfUser().getName();
		String displayName = StringUtils.isNotBlank(player.getDisplayName()) ? MessageUtil.strip(player.getDisplayName()) : "";

		BiFunction<String, Boolean, String> translator = (content, needsEscape) -> {
			if (content == null)
				return null;
			content = content.replaceAll("%time%|%date%", TimeUtil.timeStamp()).replace("%username%", needsEscape ? DiscordUtil.escapeMarkdown(player.getName()) : player.getName())
					.replace("%displayname%", needsEscape ? DiscordUtil.escapeMarkdown(displayName) : displayName).replace("%usernamenoescapes%", player.getName())
					.replace("%displaynamenoescapes%", displayName).replace("%world%", player.getWorld().getName()).replace("%reason%", reason).replace("%cible%", reported.getName())
					.replace("%botname%", botName).replace("%embedavatarurl%", avatarUrl).replace("%botavatarurl%", botAvatarUrl);
			content = PlaceholderUtil.replacePlaceholdersToDiscord(content, player);
			return content;
		};
		Message discordMessage = DiscordSRV.translateMessage(messageFormat, translator);
		if (discordMessage == null)
			return;

		DiscordUtil.queueMessage(jda.getTextChannelById(1190977034667696209l), discordMessage);
	}
}
