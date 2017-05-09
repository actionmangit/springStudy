package com.hyoungki.study.service;

import com.hyoungki.study.domain.User;

public interface UserLevelUpgradePolicy {
	boolean	canUpgradeLevel(User user);
	void	upgradeLevel(User user);
}
