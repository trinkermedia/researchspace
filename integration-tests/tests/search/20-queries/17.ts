/**
 * ResearchSpace
 * Copyright (C) 2015-2020, © Trustees of the British Museum
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import { WebDriver } from 'selenium-webdriver';
import { Test } from 'tap';
import { Options } from '../../options';
import { testSearch } from '../util';
import { sameSets } from '../../util';

/**
 * @author Artem Kozlov <ak@metaphacts.com>
 */
export async function test_17(driver: WebDriver, test: Test, options: Options) {
  await test.test(
    '17) Find: Things has material type marble and created on Year 700 BC - Year 100 BC and created at Greece and "kouros"',
    async (t: Test) => await testSearch(driver, t, options, async (s, d) => {
      await s.setDomain('Thing');
      await s.setRange('Concept');
      await s.setRelation('has material type');
      await s.typeRange('marble');
      await s.toggleRangeNode('marble');
      await s.submitRangeTree();

      const c1 = await s.getLastClause();
      await s.addAndClause(c1);
      await s.setRange('Time');
      await s.setRelation('created on');
      await s.setDateFormat('YearRange');
      await s.setYearRange('700/BC', '100/BC');

      const c2 = await s.getLastClause();
      await s.addAndClause(c2);
      await s.setRange('Place');
      await s.setRelation('created at');
      await s.typeRange('greece');
      await s.toggleRangeNode('Greece');
      await s.submitRangeTree();

      const c3 = await s.getLastClause();
      await s.addAndClause(c3);
      await s.setRange('Text search');
      await s.typeTextRange('kouros');

      const results = await s.getSearchResults();
      await sameSets(t, results, [
        '<http://collection.britishmuseum.org/id/object/GAA3387>',
        '<http://collection.britishmuseum.org/id/object/GAA5829>'
      ]);
    }));
}
